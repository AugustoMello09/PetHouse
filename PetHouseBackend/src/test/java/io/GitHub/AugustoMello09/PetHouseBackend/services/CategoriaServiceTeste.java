package io.GitHub.AugustoMello09.PetHouseBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CategoriaDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Categoria;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CategoriaDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CategoriaProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CategoriaRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.serviceImpl.CategoriaServiceImpl;

@SpringBootTest
public class CategoriaServiceTeste {

	private static final long ID = 1L;
	
	@InjectMocks
	private CategoriaServiceImpl service;
	
	@Mock
	private CategoriaRepository repository;
	
	@Mock
	private ModelMapper mapper;
	
	private CategoriaProvider categoriaProvider;
	private CategoriaDTOProvider categoriaDTOProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CategoriaServiceImpl(repository, mapper);
		categoriaProvider = new CategoriaProvider();
		categoriaDTOProvider = new CategoriaDTOProvider();
	}
	
	@DisplayName("Deve retornar um Categoria com sucesso.")
	@Test
	public void shouldReturnACategoriaWithSuccess() {
		CategoriaDTO categoriaDTO = categoriaDTOProvider.criar();
		Categoria categoria = categoriaProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(categoria));
		when(mapper.map(categoria, CategoriaDTO.class)).thenReturn(categoriaDTO);

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(CategoriaDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@DisplayName("Deve retornar Categoria não encontrado.")
	@Test
	public void shouldReturnCategoriaNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar uma lista de categorias.")
	@Test
	public void whenFindAllThenReturnCategoriaDTO() {
		List<Categoria> categorias = Arrays.asList(new Categoria(ID, "BrinBrin"));
		when(repository.findAll()).thenReturn(categorias);
		List<CategoriaDTO> result = service.listAll();
		assertNotNull(result);
	}

	@DisplayName("Deve criar uma categoria com sucesso.")
	@Test
	public void whenCreateThenReturnCategoriaDTO() {
		CategoriaDTO categoriaDTO = categoriaDTOProvider.criar();
		Categoria categoria = categoriaProvider.criar();

		when(repository.save(any(Categoria.class))).thenReturn(categoria);
		when(mapper.map(categoria, CategoriaDTO.class)).thenReturn(categoriaDTO);

		service.create(categoriaDTO);

		verify(repository, times(1)).save(any(Categoria.class));
	}

	@DisplayName("Atualização Deve retornar categoria não encontrado.")
	@Test
	public void shouldUpdateReturnCategoriaNotFound() {
		CategoriaDTO categoriaDTO = categoriaDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.updateCategoria(categoriaDTO, ID));
	}

	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {
		CategoriaDTO categoriaDTO = categoriaDTOProvider.criar();
		Categoria categoria = categoriaProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(categoria));
		when(repository.save(any(Categoria.class))).thenReturn(categoria);
		when(mapper.map(categoria, CategoriaDTO.class)).thenReturn(categoriaDTO);
		service.updateCategoria(categoriaDTO, ID);
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Categoria.class));
	}

	@DisplayName("Deve deletar uma categoria com sucesso.")
	@Test
	public void shouldCategoriaWithSuccess() {
		Categoria categoria = categoriaProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(categoria));
		service.deleteCategoria(ID);
	}

	@DisplayName("Deve não encontrar uma categoria ao deletar.")
	@Test
	public void shouldReturnRoleNotFoundWhenDelete() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.deleteCategoria(ID));
	}

}
