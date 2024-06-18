package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CarrinhoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.services.CarrinhoService;

@SpringBootTest
public class CarrinhoControllerTeste {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	private static final Long IDPRODUTO = 1L;
	
	@InjectMocks
	private CarrinhoController controller;
	
	@Mock
	private CarrinhoService service;
	
	private CarrinhoDTOProvider carrinhoDTOProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		carrinhoDTOProvider = new CarrinhoDTOProvider();
		controller = new CarrinhoController(service);
	}
	
	@DisplayName("Deve retornar um carrinho. ")
	@Test
	public void shouldReturnCarrinhoDTO() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOProvider.criar();
		when(service.findById(ID)).thenReturn(carrinhoDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(CarrinhoDTO.class, response.getBody().getClass());
	}
	
	@DisplayName("Deve adicionar um Produto ao carrinho. ")
	@Test
	public void ShouldAddProtudoOnCarrinho() {
		CarrinhoDTO carrinhoDTO = carrinhoDTOProvider.criar();
		doNothing().when(service).adicionarAoCarrinho(carrinhoDTO);
		ResponseEntity<Void> response = controller.adicionarProdutos(carrinhoDTO);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@DisplayName("Deve remover um Produto do carrinho. ")
	@Test
	public void ShouldRemoveItemFromCarrinho() {
		doNothing().when(service).removerProdutoDoCarrinho(ID, IDPRODUTO);
		ResponseEntity<Void> response = controller.removerProduto(ID, IDPRODUTO);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
