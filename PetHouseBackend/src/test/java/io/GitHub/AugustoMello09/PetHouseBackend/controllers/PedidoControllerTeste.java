package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.PedidoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.services.PedidoService;

@SpringBootTest
public class PedidoControllerTeste {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	@Mock
	private PedidoService service;
	
	@InjectMocks
	private PedidoController controller;
	
	private PedidoDTOProvider pedidoDTOProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		pedidoDTOProvider = new PedidoDTOProvider();
		controller = new PedidoController(service);
	}
	
	@DisplayName("Deve retornar o Pedido")
	@Test
	public void shouldReturnPedidoDTO() {
		PedidoDTO pedido = pedidoDTOProvider.criar();
		when(service.findById(ID)).thenReturn(pedido);
		service.findById(ID);
		assertNotNull(HttpStatus.OK);
	}
	
	@DisplayName("Deve criar o Pedido")
	@Test
	public void shouldCreatePedidoDTO() {
		PedidoDTO pedido = pedidoDTOProvider.criar();
		when(service.create(pedido)).thenReturn(pedido);
		var response = controller.create(pedido);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(PedidoDTO.class, response.getBody().getClass());
	}
}
