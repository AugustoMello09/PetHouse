package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CategoriaDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Categoria;
import io.gitHub.AugustoMello09.PetHouse.provider.CategoriaDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.CategoriaProvider;
import io.gitHub.AugustoMello09.PetHouse.repositories.CategoriaRepository;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.CategoriaServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {
	
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
		Categoria categoria = categoriaProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(categoria));

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
		when(mapper.map(any(Categoria.class), eq(CategoriaDTO.class))).thenReturn(categoriaDTO);
	  
	    CategoriaDTO result = service.create(categoriaDTO);

	    assertEquals(categoriaDTO, result);

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
