package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.ProdutoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.ProdutoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.services.ProdutoService;

@SpringBootTest
public class ProdutoControllerTeste {
	
	private static final long ID = 1L;
	
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
	public void shouldReturnCreatedProdutoDTOOnController() {
		ProdutoDTO produtoDTO = produtoDTOProvider.criar();
		when(service.create(produtoDTO)).thenReturn(produtoDTO);
		var response = controller.create(produtoDTO);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(ProdutoDTO.class, response.getBody().getClass());
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

}
