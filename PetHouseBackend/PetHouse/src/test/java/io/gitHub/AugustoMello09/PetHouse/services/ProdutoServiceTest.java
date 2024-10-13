package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Categoria;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Tipo;
import io.gitHub.AugustoMello09.PetHouse.provider.CategoriaProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.ProdutoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.ProdutoProvider;
import io.gitHub.AugustoMello09.PetHouse.repositories.CategoriaRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.ProdutoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.ProdutoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {

	private static final long ID = 1L;
	private static final Tipo TIPO = Tipo.CACHORRO;
	private static final BigDecimal PRECO = new BigDecimal(91.21);
	private static final String DESCRICAO = "Simparic 20mg contém sarolaner e começa a agir 3h após a administração, sendo eficaz por até 35 dias contra infestações após o tratamento. Confira a bula para mais informações sobre a eficácia do medicamento.";
	private static final String NOME = "Antipulgas Simparic 5 a 10kg Cães 20mg 1 comprimido";

	@InjectMocks
	private ProdutoServiceImpl service;

	@Mock
	private ProdutoRepository repository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private CategoriaRepository categoriaRepository;
	
	@Mock
	private S3Service s3Service;
	
	@Mock
	private ImgService imgService;

	private ProdutoProvider produtoProvider;
	private ProdutoDTOProvider produtoDTOProvider;
	private CategoriaProvider categoriaProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new ProdutoServiceImpl(repository, modelMapper, categoriaRepository, s3Service, imgService);
		produtoProvider = new ProdutoProvider();
		produtoDTOProvider = new ProdutoDTOProvider();
		categoriaProvider = new CategoriaProvider();
	}

	@DisplayName("Deve retornar um Usuario com sucesso.")
	@Test
	public void shouldReturnAUserWithSuccess() {
		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		Produto produto = produtoProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(produto));
		when(modelMapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(ProdutoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@DisplayName("Deve retornar produto não encontrado.")
	@Test
	public void shouldReturnProdutoNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar paginação de produtos.")
	@Test
	public void whenFindAllPagedThenReturnPageOfProdutoDTO() {
		List<Produto> pro = Arrays.asList(new Produto(ID, NOME, PRECO, DESCRICAO, TIPO, null, null));
		Page<Produto> proPage = new PageImpl<>(pro);
		when(repository.findAll(any(Pageable.class))).thenReturn(proPage);
		Page<ProdutoDTO> result = service.findAllPaged(PageRequest.of(0, 5));
		assertNotNull(result);
	}

	@DisplayName("Deve criar um produto com sucesso.")
	@Test
	public void whenCreateThenReturnProdutoDTO() {

		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		Produto produto = produtoProvider.criar();

		when(repository.save(any(Produto.class))).thenReturn(produto);
		when(modelMapper.map(any(Produto.class), eq(ProdutoDTO.class))).thenReturn(produtoDTO);

		service.create(produtoDTO);

		verify(repository, times(1)).save(any(Produto.class));
	}

	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {
		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		Produto produto = produtoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(produto));
		when(repository.save(any(Produto.class))).thenReturn(produto);
		when(modelMapper.map(produto, ProdutoDTO.class)).thenReturn(produtoDTO);
		service.updateProduto(produtoDTO, ID);
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Produto.class));
	}

	@DisplayName("Deve deletar um produto com sucesso.")
	@Test
	public void shouldDeleteWithSuccess() {
		Produto produto = produtoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(produto));
		service.deleteProduto(ID);
	}

	@DisplayName("Deve não encontrar um produto ao deletar.")
	@Test
	public void shouldReturnProdutoNotFoundWhenDelete() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.deleteProduto(ID));
	}

	@DisplayName("Deve não encontrar um produto ao atualizar.")
	@Test
	public void shouldReturnProdutoNotFoundUpdate() {
		ProdutoDTO produto = produtoDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.updateProduto(produto, ID);
		});
		assertEquals("Produto não encontrado", exception.getMessage());
	}

	@DisplayName("Deve não encontrar um produto ao associar a categoria.")
	@Test
	public void shouldReturnProdutoNotFoundWhenAsso() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.atribuirCategoria(ID, ID);
		});
		assertEquals("Produto não encontrado", exception.getMessage());
	}

	@DisplayName("Deve não encontrar uma categoria.")
	@Test
	public void shouldReturnCategoriaNotFoundWhenAssoProduto() {

		Produto produto = produtoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(produto));
		when(categoriaRepository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.atribuirCategoria(ID, ID);
		});
		assertEquals("Categoria não encontrado", exception.getMessage());
	}

	@DisplayName("Deve associar a categoria.")
	@Test
	public void shoulAssignCategoryWithSuccess() {
		Produto produto = produtoProvider.criar();
		Categoria categoria = categoriaProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(produto));
		when(categoriaRepository.findById(ID)).thenReturn(Optional.of(categoria));

		service.atribuirCategoria(ID, ID);

		verify(categoriaRepository, times(1)).findById(ID);
		verify(repository, times(1)).findById(ID);

	}

	@DisplayName("Deve não encontrar uma categoria ao buscar a lista de produtos.")
	@Test
	public void shouldReturnCategoriaNotFoundWhenFindByProduto() {
		when(categoriaRepository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findByCategoriaIdOrderByNome(ID);
		});
		assertEquals("Categoria não encontrada", exception.getMessage());
	}

	@DisplayName("Deve buscar a lista de produtos por categorias.")
	@Test
	public void shoulFindByCategoryListWithSuccess() {
		Categoria categoria = categoriaProvider.criar();
		when(categoriaRepository.findById(ID)).thenReturn(Optional.of(categoria));
		Produto produto = produtoProvider.criar();
		produto.setCategoria(categoria);
		List<ProdutoDTO> response = service.findByCategoriaIdOrderByNome(ID);
		assertNotNull(response);
		verify(categoriaRepository, times(1)).findById(ID);
	}

	@DisplayName("Deve retornar uma lista de produtos pelo nome")
	@Test
	public void shoulReturnListOfProdutoByNome() {
		List<Produto> produto = new ArrayList<>();
		when(repository.findByNomeContainingIgnoreCase(NOME)).thenReturn(produto);
		List<ProdutoDTO> response = service.findByNomeContaining(NOME);
		assertNotNull(response);
	}

}
