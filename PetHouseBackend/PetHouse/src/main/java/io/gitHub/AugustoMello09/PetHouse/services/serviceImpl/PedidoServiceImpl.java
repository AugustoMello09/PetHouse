package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PedidoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Historico;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinhoProduto;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemPedido;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Pedido;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Pagamento;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Status;
import io.gitHub.AugustoMello09.PetHouse.infra.message.producer.PagamentoProducer;
import io.gitHub.AugustoMello09.PetHouse.repositories.CarrinhoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.HistoricoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.PedidoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.PedidoService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

	private final PedidoRepository repository;
	private final ModelMapper mapper;
	private final CarrinhoRepository carrinhoRepository;
	private final PagamentoProducer producer;
	private final HistoricoRepository historicoRepository;

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM', 'ROLE_OPERATOR')")
	public PedidoDTO findById(UUID id) {
		Optional<Pedido> entity = repository.findById(id);
		Pedido pedido = entity.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
		return mapper.map(pedido, PedidoDTO.class);
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM','ROLE_OPERATOR')")
	public PedidoDTO create(UUID idCarrinho, Integer pagamento) {
	    
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho)
	            .orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));

	    Pedido pedido = new Pedido();
	    pedido.setUsuario(carrinho.getUsuario());
	    pedido.setData(LocalDate.now());
	    pedido.setCarrinho(carrinho);
	    pedido.setPagamento(Pagamento.toEnum(pagamento));
	    pedido.setStatus(Status.PENDENTE);

	    BigDecimal valorTotal = BigDecimal.ZERO;

	    for (ItemCarrinhoProduto itemCarrinho : carrinho.getItemsCarrinho()) {
	        ItemPedido itemPedido = new ItemPedido();
	        itemPedido.setProduto(itemCarrinho.getProduto());
	        itemPedido.setQuantidade(itemCarrinho.getQuantidade());
	        itemPedido.setPreco(itemCarrinho.getPreco());
	        itemPedido.setImg(itemCarrinho.getImg());
	       
	        itemPedido.setPedido(pedido);
	        pedido.getItemsPedido().add(itemPedido);

	        valorTotal = valorTotal.add(itemCarrinho.getPreco().multiply(new BigDecimal(itemCarrinho.getQuantidade())));
	    }
	    
	    if(Pagamento.CREDIT_CARD.equals(pedido.getPagamento())) {
	    	pedido.setStatus(Status.APROVADO);
	    	carrinho.getItemsCarrinho().clear();
	    	
	    	Historico historico = historicoRepository.findById(carrinho.getUsuario().getHistorico().getId())
	    			.orElseThrow(() -> new ObjectNotFoundException("Histórioco não encontrado "));
	    	
	    	historico.getPedidos().add(pedido);
	    }
	    
	    repository.save(pedido);
	    producer.sendToTopicCarrinho(pedido);
	    carrinhoRepository.save(carrinho); 

	    return mapper.map(pedido, PedidoDTO.class);
	}

}
