package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinhoProduto;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.infra.message.producer.CarrinhoProducer;
import io.gitHub.AugustoMello09.PetHouse.provider.CarrinhoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.CarrinhoProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.ProdutoProvider;
import io.gitHub.AugustoMello09.PetHouse.repositories.CarrinhoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.ProdutoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.CarrinhoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CarrinhoServiceTest {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private static final Long IDPRODUTO = 1L;

	@InjectMocks
	private CarrinhoServiceImpl service;

	@Mock
	private CarrinhoRepository repository;

	@Mock
	private ModelMapper mapper;

	@Mock
	private ProdutoRepository produtoRepository;
	
	@Mock
	private CarrinhoProducer producer;

	private CarrinhoProvider carrinhoProvider;
	private CarrinhoDTOProvider carrinhoDTOPrivider;
	private ProdutoProvider produtoProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CarrinhoServiceImpl(repository, mapper, produtoRepository, producer);
		carrinhoDTOPrivider = new CarrinhoDTOProvider();
		carrinhoProvider = new CarrinhoProvider();
		produtoProvider = new ProdutoProvider();
	}

	@DisplayName("Deve retornar um carrinho")
	@Test
	public void shouldReturnCarrinhoDTO() {
		Carrinho carrinho = carrinhoProvider.criar();
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(mapper.map(carrinho, CarrinhoDTO.class)).thenReturn(carrinhoDTO);
		service.findById(ID);
		verify(repository, times(1)).findById(any(UUID.class));
	}

	@DisplayName("Deve retornar carrinho não encontrado")
	@Test
	public void shouldReturnCarrinhoNotFound() {
		when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById(ID);
		});
		assertEquals("Carrinho não encontrado", exception.getMessage());
	}

	@DisplayName("Deve retornar carrinho não encontrado ao tentar remover")
	@Test
	public void RemoveItemToCarrinhoShouldReturnCarrinhoNotFound() {
		when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.removerProdutoDoCarrinho(ID, IDPRODUTO);
		});
		assertEquals("Carrinho não encontrado", exception.getMessage());
	}

	@DisplayName("Deve retornar produto não encontrado ao tentar remover")
	@Test
	public void RemoveItemToCarrinhoShouldReturnProdutoNotFound() {
		Carrinho carrinho = carrinhoProvider.criar();
		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.removerProdutoDoCarrinho(ID, IDPRODUTO);
		});
		assertEquals("Produto não encontrado no carrinho", exception.getMessage());
	}

	@DisplayName("Deve remover produto do carrinho com sucesso")
	@Test
	public void ShouldRemoveItemFromCarrinhoWithSuccess() {
		Carrinho carrinho = carrinhoProvider.criar();
		Produto produto = produtoProvider.criar();
		ItemCarrinhoProduto itemCarrinho = new ItemCarrinhoProduto(carrinho, produto);
		carrinho.getItemsCarrinho().add(itemCarrinho);

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(repository.save(any(Carrinho.class))).thenReturn(carrinho);

		service.removerProdutoDoCarrinho(carrinho.getId(), produto.getId());

		assertEquals(0, carrinho.getItemsCarrinho().size());

		verify(repository).findById(any(UUID.class));
		verify(repository).save(any(Carrinho.class));
	}

	@DisplayName("Deve remover o produto do carrinho quando quantidade é 1")
	@Test
	public void shouldRemoveProductFromCartWhenQuantityIsOne() {
		Carrinho carrinho = carrinhoProvider.criar();
		Produto produto = produtoProvider.criar();
		ItemCarrinhoProduto itemCarrinho = new ItemCarrinhoProduto(carrinho, produto);
		itemCarrinho.setQuantidade(1);
		carrinho.getItemsCarrinho().add(itemCarrinho);

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(repository.save(any(Carrinho.class))).thenReturn(carrinho);

		service.removerProdutoDoCarrinho(carrinho.getId(), produto.getId());

		assertEquals(0, carrinho.getItemsCarrinho().size());

		verify(repository).findById(any(UUID.class));
		verify(repository).save(any(Carrinho.class));
	}

	@DisplayName("Deve diminuir a quantidade do produto no carrinho")
	@Test
	public void shouldDecreaseProductQuantityInCart() {
		Carrinho carrinho = carrinhoProvider.criar();
		Produto produto = produtoProvider.criar();
		ItemCarrinhoProduto itemCarrinho = new ItemCarrinhoProduto(carrinho, produto);
		itemCarrinho.setQuantidade(2);
		carrinho.getItemsCarrinho().add(itemCarrinho);

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(repository.save(any(Carrinho.class))).thenReturn(carrinho);

		service.removerProdutoDoCarrinho(carrinho.getId(), produto.getId());

		assertEquals(1, carrinho.getItemsCarrinho().get(0).getQuantidade());

		verify(repository).findById(any(UUID.class));
		verify(repository).save(any(Carrinho.class));
	}

	@Test
	public void adicionarAoCarrinho_CarrinhoEncontradoEProdutoEncontrado_AdicionaNovoItem() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		Carrinho carrinho = carrinhoProvider.criar();
		Produto produto = produtoProvider.criar();

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
		when(mapper.map(any(Carrinho.class), eq(CarrinhoDTO.class))).thenReturn(carrinhoDTO);

		CarrinhoDTO result = service.adicionarAoCarrinho(carrinhoDTO);

		assertNotNull(result);
		verify(repository, times(1)).save(carrinho);
		assertEquals(1, carrinho.getItemsCarrinho().size());
	}

	@Test
	public void adicionarAoCarrinho_CarrinhoEncontradoEProdutoEncontrado_IncrementaQuantidade() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		Carrinho carrinho = carrinhoProvider.getCarrinhoComItem();
		Produto produto = produtoProvider.criar();

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.of(produto));
		when(mapper.map(any(Carrinho.class), eq(CarrinhoDTO.class))).thenReturn(carrinhoDTO);

		CarrinhoDTO result = service.adicionarAoCarrinho(carrinhoDTO);

		assertNotNull(result);
		verify(repository, times(1)).save(carrinho);
		assertEquals(1, carrinho.getItemsCarrinho().size());
		assertEquals(2, carrinho.getItemsCarrinho().get(0).getQuantidade());
	}

	@Test
	public void adicionarAoCarrinho_CarrinhoNaoEncontrado_LancaException() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.adicionarAoCarrinho(carrinhoDTO));
		verify(repository, never()).save(any(Carrinho.class));
	}
	
	@Test
	public void adicionarAoCarrinho_ProdutoNaoEncontrado_LancaException() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		Carrinho carrinho = carrinhoProvider.criar();

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(produtoRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		assertThrows(ObjectNotFoundException.class, () -> service.adicionarAoCarrinho(carrinhoDTO));
		verify(repository, never()).save(any(Carrinho.class));
	}



}
