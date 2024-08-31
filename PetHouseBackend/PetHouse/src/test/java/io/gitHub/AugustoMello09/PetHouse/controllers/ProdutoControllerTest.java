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
import java.util.Collections;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;
import io.gitHub.AugustoMello09.PetHouse.provider.ProdutoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.services.ProdutoService;

@ExtendWith(MockitoExtension.class)
public class ProdutoControllerTest {
	
	private static final long ID = 1L;
	private static final String NOME = "Antipulgas Simparic 5 a 10kg Cães 20mg 1 comprimido";
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private ProdutoController controller;
	
	@Mock
	private ProdutoService service;
	
	private ProdutoDTOProvider produtoDTOProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		produtoDTOProvider = new ProdutoDTOProvider();
		controller = new ProdutoController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		objectMapper = new ObjectMapper();
	}
	
	@DisplayName("Deve retornar um produto. ")
	@Test
	public void shouldControllerReturnFindById() {
		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		when(service.findById(ID)).thenReturn(produtoDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ProdutoDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve criar um Protudo. ")
	@Test
	public void shouldReturnCreatedProdutoDTOOnController() throws Exception {
		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		when(service.create(any(ProdutoDTO.class))).thenReturn(produtoDTO);
		mockMvc.perform(post("/v1/produto/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(produtoDTO))).andExpect(status().isCreated());
		
	}

	@DisplayName("Deve atualizar o produto. ")
	@Test
	public void shouldReturnUpdateProdutoDTOOnController() {
		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		doNothing().when(service).updateProduto(produtoDTO, ID);
		ResponseEntity<Void> response = controller.update(produtoDTO, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).updateProduto(produtoDTO, ID);
	}
	
	
	@DisplayName("Deve retornar uma paginação de produtos. ")
	@Test
	public void shouldReturnPagedProdutoDTO() {
		Pageable pageable = PageRequest.of(0, 10); 
		Page<ProdutoDTO> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
		when(service.findAllPaged(pageable)).thenReturn(page);
		ResponseEntity<Page<ProdutoDTO>> response = controller.findAllPaged(pageable);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(page, response.getBody());
		verify(service).findAllPaged(pageable);
	}
	
	@DisplayName("Deve deletar um produto. ")
	@Test
	public void shouldDeleteProdutoWithSuccess() {
		doNothing().when(service).deleteProduto(ID);
		ResponseEntity<Void> response = controller.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service, times(1)).deleteProduto(ID);;
	}
	
	@DisplayName("Deve associar um produto a categoria. ")
	@Test
	public void shouldAssociProdutoWithSuccess() {
		doNothing().when(service).atribuirCategoria(ID, ID);
		ResponseEntity<Void> response = controller.atribuirCategoria(ID, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).atribuirCategoria(ID, ID);
	}
	
	@DisplayName("Deve buscar uma lista de produtos por categoria. ")
	@Test
	public void shouldFindByCategoriaProdutoWithSuccess() {
		List<ProdutoDTO> listaDTO = new ArrayList<>();
		when(service.findByCategoriaIdOrderByNome(ID)).thenReturn(listaDTO);
		ResponseEntity<List<ProdutoDTO>> response = controller.findByCategoriaIdOrderByNome(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).findByCategoriaIdOrderByNome(ID);
	}
	
	@DisplayName("Deve buscar uma lista de produtos por nome. ")
	@Test
	public void shouldFindByNomeProdutoWithSuccess() {
		List<ProdutoDTO> listaDTO = new ArrayList<>();
		when(service.findByNomeContaining(NOME)).thenReturn(listaDTO);
		ResponseEntity<List<ProdutoDTO>> response = controller.findByNomeContaining(NOME);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}


}
