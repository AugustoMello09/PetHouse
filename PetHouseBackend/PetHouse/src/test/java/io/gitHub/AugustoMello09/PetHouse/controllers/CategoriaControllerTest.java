package io.gitHub.AugustoMello09.PetHouse.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CategoriaDTO;
import io.gitHub.AugustoMello09.PetHouse.provider.CategoriaDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.services.CategoriaService;

@ExtendWith(MockitoExtension.class)
public class CategoriaControllerTest {
	
	private static final long ID = 1L;

	@InjectMocks
	private CategoriaController controller;

	@Mock
	private CategoriaService service;

	private CategoriaDTOProvider categoriaDTOPrivder;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		categoriaDTOPrivder = new CategoriaDTOProvider();
		controller = new CategoriaController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		objectMapper = new ObjectMapper();
	}

	@DisplayName("Deve retornar uma categoria. ")
	@Test
	public void shouldControllerReturnFindById() {
		CategoriaDTO categoriaDTO = categoriaDTOPrivder.criar();
		when(service.findById(ID)).thenReturn(categoriaDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(CategoriaDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve criar uma categoria. ")
	@Test
	public void shouldReturnCreatedCategoriaDTOOnController() throws Exception {
		CategoriaDTO categoriaDTO = categoriaDTOPrivder.criar();
		when(service.create(any(CategoriaDTO.class))).thenReturn(categoriaDTO);
		mockMvc.perform(post("/v1/categoria/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoriaDTO))).andExpect(status().isCreated());
	}

	@DisplayName("Deve atualizar uma categoria. ")
	@Test
	public void shouldReturnUpdateCategoriaDTOOnController() {
		CategoriaDTO categoriaDTO = categoriaDTOPrivder.criar();
		doNothing().when(service).updateCategoria(categoriaDTO, ID);
		ResponseEntity<Void> response = controller.update(categoriaDTO, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).updateCategoria(categoriaDTO, ID);
	}

	@DisplayName("Deve retornar uma Lista de categorias. ")
	@Test
	public void shouldReturnListCategoriaDTO() {
		List<CategoriaDTO> categorias = new ArrayList<>();
		when(service.listAll()).thenReturn(categorias);
		ResponseEntity<List<CategoriaDTO>> response = controller.listAll();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service).listAll();
	}

	@DisplayName("Deve deletar uma categoria ")
	@Test
	public void shouldDeleteCategoriaWithSuccess() {
		doNothing().when(service).deleteCategoria(ID);
		ResponseEntity<Void> response = controller.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service, times(1)).deleteCategoria(ID);
		;
	}

}
