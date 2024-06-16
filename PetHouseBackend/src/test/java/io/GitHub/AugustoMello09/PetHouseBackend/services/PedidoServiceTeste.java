package io.GitHub.AugustoMello09.PetHouseBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Pedido;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.PedidoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.PedidoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.ProdutoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.UsuarioProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CarrinhoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.PedidoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.ProdutoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class PedidoServiceTeste {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	@InjectMocks
	private PedidoServiceImpl service;
	
	@Mock
	private PedidoRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ProdutoRepository produtoRepository;
	
	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private CarrinhoRepository carrinhoRepository;
	
	private PedidoProvider pedidoProvider;
	private PedidoDTOProvider pedidoDTOProvider;
	private UsuarioProvider usuarioProvider;
	private ProdutoProvider produtoProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new PedidoServiceImpl(repository, mapper, usuarioRepository, produtoRepository, carrinhoRepository);
		pedidoProvider = new PedidoProvider();
		pedidoDTOProvider = new PedidoDTOProvider();
		produtoProvider = new ProdutoProvider();
		usuarioProvider = new UsuarioProvider(passwordEncoder);
	}
	
	@DisplayName("Deve retornar um PedidoDTO")
	@Test
	public void shouldReturnPedidoDTOWithSuccess() {
		Pedido pedido = pedidoProvider.criar();
		PedidoDTO pedidoDTO = pedidoDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(pedido));
		when(mapper.map(pedidoDTO, PedidoDTO.class)).thenReturn(pedidoDTO);
		service.findById(ID);
		verify(repository, times(1)).findById(ID);
	}
	
	@DisplayName("Deve retornar Pedido não encontrado")
	@Test
	public void shouldReturnPedidoNotfound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById(ID);
		});
		assertEquals("Pedido não encontrado", exception.getMessage());
	}
	
	@DisplayName("Deve retornar usuário não encontrado ao criar pedido")
	@Test
	public void shouldReturnUserNotFoundWhenAssOnPedido() {
		Pedido pedido = pedidoProvider.criar();
		PedidoDTO pedidoDTO = pedidoDTOProvider.criar();
		when(usuarioRepository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.atribuirUsuario(pedido, pedidoDTO);
		});
		assertEquals("Usuário não encontrado", exception.getMessage());
	}
	
	@DisplayName("Deve retornar Produto não encontrado ao criar pedido")
	@Test
	public void shouldReturnProductNotFoundWhenAssOnPedido() {
		Pedido pedido = pedidoProvider.criar();
		PedidoDTO pedidoDTO = pedidoDTOProvider.criar();
		pedidoDTO.getProdutos().forEach(produtoDTO -> {
            when(produtoRepository.findById(produtoDTO.getId())).thenReturn(Optional.empty());
		});
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.atribuirProdutos(pedido, pedidoDTO);
		});
		assertEquals("Produto não encontrado", exception.getMessage());
	}
	
	@DisplayName("Deve criar um pedido")
	@Test
	public void shouldCreatePedidoWithSuccess() {
		Pedido pedido = pedidoProvider.criar();
		PedidoDTO pedidoDTO = pedidoDTOProvider.criar();
		Produto produto = produtoProvider.criar();
		Usuario usuario = usuarioProvider.criar();
		
		pedidoDTO.getProdutos().forEach(produtoDTO -> {
            when(produtoRepository.findById(produtoDTO.getId())).thenReturn(Optional.of(produto));
		});
		when(usuarioRepository.findById(ID)).thenReturn(Optional.of(usuario));
		when(repository.save(any(Pedido.class))).thenReturn(pedido);
		when(mapper.map(pedido, PedidoDTO.class)).thenReturn(pedidoDTO);
		
		service.create(pedidoDTO);

		verify(repository, times(1)).save(any(Pedido.class));
	}
}
