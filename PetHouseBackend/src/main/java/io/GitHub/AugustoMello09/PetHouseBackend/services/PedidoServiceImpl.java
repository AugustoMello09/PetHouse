package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Pedido;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CarrinhoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.PedidoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.ProdutoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoServiceImpl implements PedidoService {

	private final PedidoRepository repository;
	private final ModelMapper mapper;
	private final UsuarioRepository usuarioRepository;
	private final ProdutoRepository produtoRepository;
	private final CarrinhoRepository carrinhoRepository;

	public PedidoServiceImpl(PedidoRepository repository, ModelMapper mapper, UsuarioRepository usuarioRepository,
			ProdutoRepository produtoRepository, CarrinhoRepository carrinhoRepository) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.usuarioRepository = usuarioRepository;
		this.produtoRepository = produtoRepository;
		this.carrinhoRepository = carrinhoRepository;
	}

	@Override
	public PedidoDTO findById(UUID id) {
		Optional<Pedido> entity = repository.findById(id);
		Pedido pedido = entity.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado"));
		return mapper.map(pedido, PedidoDTO.class);
	}

	@Override
	public PedidoDTO create(PedidoDTO pedidoDTO) {
		Pedido entity = new Pedido();
		entity.setData(LocalDate.now());
		atribuirProdutos(entity, pedidoDTO);
		atribuirUsuario(entity, pedidoDTO);
		atribuirCarrinho(entity, pedidoDTO);
		repository.save(entity);
		return mapper.map(entity, PedidoDTO.class);
	}

	protected void atribuirUsuario(Pedido pedido, PedidoDTO pedidoDTO) {
		UUID idUsuario = pedidoDTO.getIdUsuario();
		Usuario usuario = usuarioRepository.findById(idUsuario)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		pedido.setUsuario(usuario);	
	}

	protected void atribuirProdutos(Pedido pedido, PedidoDTO pedidoDTO) {
		pedido.getProdutos()
		.addAll(pedidoDTO.getProdutos().stream()
				.map(x -> produtoRepository.findById(x.getId())
						.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado")))
				.collect(Collectors.toList()));
	}
	
	protected void atribuirCarrinho(Pedido pedido, PedidoDTO pedidoDTO) {
		UUID idCarrinho = pedidoDTO.getIdCarrinho();
		Carrinho carrinho = carrinhoRepository.findById(idCarrinho)
				.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));
		pedido.setCarrinho(carrinho);
	}

}
