package io.GitHub.AugustoMello09.PetHouseBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
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

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CarrinhoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CarrinhoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.ProdutoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CarrinhoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.ProdutoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class CarrinhoServiceTeste {

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

	private CarrinhoProvider carrinhoProvider;
	private CarrinhoDTOProvider carrinhoDTOPrivider;
	private ProdutoProvider produtoProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CarrinhoServiceImpl(repository, mapper, produtoRepository);
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
			service.findById(any(UUID.class));
		});
		assertEquals("Carrinho não encontrado", exception.getMessage());
	}

	@DisplayName("Deve retornar carrinho não encontrado")
	@Test
	public void shouldReturnCarrinhoNotFoundWhenAddProducts() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.adicionarAoCarrinho(carrinhoDTO);
		});
		assertEquals("Carrinho não encontrado", exception.getMessage());
	}

	@DisplayName("Deve conseguir adicionar um produto ao carrinho")
	@Test
	public void shouldAddProtudoOnCarrinhoDTO() {
		Carrinho carrinho = carrinhoProvider.criar();
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		Produto produto = produtoProvider.criar();
		List<Produto> produtosParaAdicionar = List.of(produto);

		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(produtoRepository.findAllById(anyList())).thenReturn(produtosParaAdicionar);
		when(repository.save(any(Carrinho.class))).thenReturn(carrinho);
		
		service.adicionarAoCarrinho(carrinhoDTO);
		
		assertEquals(1, carrinho.getProdutos().size());  
	    assertTrue(carrinho.getProdutos().contains(produto));  

	    verify(repository).findById(any(UUID.class));  
	    verify(produtoRepository).findAllById(anyList()); 
	    verify(repository).save(any(Carrinho.class)); 
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
		when(produtoRepository.findById(IDPRODUTO)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.removerProdutoDoCarrinho(ID, IDPRODUTO);
		});
		assertEquals("Produto não encontrado", exception.getMessage());
	}
	
	@DisplayName("Deve remover produto do carrinho com sucesso")
	@Test
	public void ShouldRemoveItemFromCarrinhoWithSuccess() {
		Carrinho carrinho = carrinhoProvider.criar();
		Produto produto = produtoProvider.criar();
		carrinho.getProdutos().add(produto);
		
		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(produtoRepository.findById(IDPRODUTO)).thenReturn(Optional.of(produto));
		when(repository.save(any(Carrinho.class))).thenReturn(carrinho);
		
		service.removerProdutoDoCarrinho(carrinho.getId(), produto.getId());
		
		assertFalse(carrinho.getProdutos().contains(produto));  
	    assertEquals(0, carrinho.getProdutos().size());  

	    verify(repository).findById(any(UUID.class));  
	    verify(produtoRepository).findById(IDPRODUTO);  
	    verify(repository).save(any(Carrinho.class));
	}

}
