package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PedidoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinhoProduto;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinhoProdutoId;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Pedido;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.provider.CarrinhoProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.PedidoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.PedidoProvider;
import io.gitHub.AugustoMello09.PetHouse.repositories.CarrinhoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.PedidoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.PedidoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	private static final UUID CARRINHO_ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@InjectMocks
	private PedidoServiceImpl service;

	@Mock
	private PedidoRepository repository;

	@Mock
	private ModelMapper mapper;

	@Mock
	private CarrinhoRepository carrinhoRepository;

	private PedidoProvider pedidoProvider;
	private PedidoDTOProvider pedidoDTOProvider;
	private CarrinhoProvider carrinhoProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new PedidoServiceImpl(repository, mapper, carrinhoRepository);
		pedidoProvider = new PedidoProvider();
		pedidoDTOProvider = new PedidoDTOProvider();
		carrinhoProvider = new CarrinhoProvider();
	}

	@DisplayName("Deve retornar um PedidoDTO")
	@Test
	public void shouldReturnPedidoDTOWithSuccess() {
		Pedido pedido = pedidoProvider.criar();
		PedidoDTO pedidoDTO = pedidoDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(pedido));
		when(mapper.map(any(Pedido.class), eq(PedidoDTO.class))).thenReturn(pedidoDTO);
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

	@DisplayName("Deve criar um Pedido e retornar PedidoDTO com sucesso")
	@Test
	public void shouldCreatePedidoAndReturnPedidoDTO() {

        Carrinho carrinho = carrinhoProvider.criar();
        Pedido pedido = pedidoProvider.criar();
        PedidoDTO pedidoDTO = pedidoDTOProvider.criar();

        
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Teste");

        ItemCarrinhoProdutoId itemId = new ItemCarrinhoProdutoId(CARRINHO_ID, produto.getId());
        ItemCarrinhoProduto itemCarrinhoProduto = new ItemCarrinhoProduto(carrinho, produto);
        itemCarrinhoProduto.setId(itemId);
        itemCarrinhoProduto.setQuantidade(2);
        itemCarrinhoProduto.setPreco(BigDecimal.valueOf(100));
        itemCarrinhoProduto.setNome("Produto Teste");
        itemCarrinhoProduto.setImg("imagem_teste.png");

        
        carrinho.getItemsCarrinho().add(itemCarrinhoProduto);

       
        when(carrinhoRepository.findById(CARRINHO_ID)).thenReturn(Optional.of(carrinho));
        when(repository.save(any(Pedido.class))).thenReturn(pedido);
        when(mapper.map(any(Pedido.class), eq(PedidoDTO.class))).thenReturn(pedidoDTO);

        PedidoDTO result = service.create(CARRINHO_ID);

        assertNotNull(result);
        assertEquals(pedidoDTO.getId(), result.getId());
        assertEquals(pedidoDTO.getData(), result.getData());

        verify(carrinhoRepository, times(1)).findById(CARRINHO_ID);
        verify(repository, times(1)).save(any(Pedido.class));
        verify(mapper, times(1)).map(any(Pedido.class), eq(PedidoDTO.class));

        verify(carrinhoRepository, times(1)).save(carrinho);
        assertTrue(carrinho.getItemsCarrinho().isEmpty());

	}

	@DisplayName("Deve lançar exceção se carrinho não for encontrado")
	@Test
	public void shouldThrowExceptionWhenCarrinhoNotFound() {

		when(carrinhoRepository.findById(CARRINHO_ID)).thenReturn(Optional.empty());


		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.create(CARRINHO_ID);
		});

		assertEquals("Carrinho não encontrado", exception.getMessage());
		verify(carrinhoRepository, times(1)).findById(CARRINHO_ID);
		verify(repository, never()).save(any(Pedido.class));
	}

}
