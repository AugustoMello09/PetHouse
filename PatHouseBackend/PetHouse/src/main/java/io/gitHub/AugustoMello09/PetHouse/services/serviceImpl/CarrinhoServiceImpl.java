package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ItemCarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.repositories.CarrinhoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.ProdutoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.CarrinhoService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarrinhoServiceImpl implements CarrinhoService {
	
	private final CarrinhoRepository repository;
	private final ModelMapper mapper;
	private final ProdutoRepository produtoRepository;

	@Override
	public CarrinhoDTO findById(UUID id) {
		Optional<Carrinho> entity = repository.findById(id);
		Carrinho carrinho = entity.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));
		return mapper.map(carrinho, CarrinhoDTO.class);
	}

	@Override
	public CarrinhoDTO adicionarAoCarrinho(CarrinhoDTO carrinhoDTO) {
		 Carrinho carrinho = repository.findById(carrinhoDTO.getId())
				 .orElseThrow(()-> new ObjectNotFoundException("Carrinho não encontrado"));
		    
		 for (ItemCarrinhoDTO itemDTO : carrinhoDTO.getItemsCarrinho()) {

		        Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
		                .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));

		        
		        Optional<ItemCarrinho> carrinhoProdutoOpt = carrinho.getItemsCarrinho().stream()
		                .filter(cp -> cp.getProduto().getId().equals(produto.getId()))
		                .findFirst();

		        if (carrinhoProdutoOpt.isPresent()) {
		            ItemCarrinho carrinhoProduto = carrinhoProdutoOpt.get();
		            carrinhoProduto.setQuantidade(carrinhoProduto.getQuantidade() + 1);
		        } else {
		            ItemCarrinho carrinhoProduto = new ItemCarrinho();
		            carrinhoProduto.setCarrinho(carrinho);
		            carrinhoProduto.setProduto(produto);
		            carrinhoProduto.setQuantidade(1);
		            carrinhoProduto.setNome(produto.getNome());  
		            carrinhoProduto.setPreco(produto.getPreco());
		            carrinhoProduto.setImg(produto.getImg());
		            carrinho.getItemsCarrinho().add(carrinhoProduto);
		        }
		    }    
		    repository.save(carrinho);    
	        return mapper.map(carrinho, CarrinhoDTO.class);
	}

	@Override
	public void removerProdutoDoCarrinho(UUID idCarrinho, Long idProduto) {
	    Carrinho carrinho = repository.findById(idCarrinho)
	            .orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));

	    ItemCarrinho carrinhoPedido = carrinho.getItemsCarrinho().stream()
	            .filter(cp -> cp.getProduto().getId().equals(idProduto))
	            .findFirst()
	            .orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado no carrinho"));

	    if (carrinhoPedido.getQuantidade() > 1) {
	        carrinhoPedido.setQuantidade(carrinhoPedido.getQuantidade() - 1);
	    } else {
	        carrinho.getItemsCarrinho().remove(carrinhoPedido);
	    }
	    
	    repository.save(carrinho);
	}

}
