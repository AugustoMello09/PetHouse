package io.gitHub.AugustoMello09.PetHouse.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PedidoDTO;
import io.gitHub.AugustoMello09.PetHouse.provider.PedidoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.services.PedidoService;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {

	private static final UUID Pedido_ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@InjectMocks
	private PedidoController controller;

	@Mock
	private PedidoService service;

	private PedidoDTOProvider PedidoDTOProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		PedidoDTOProvider = new PedidoDTOProvider();
		controller = new PedidoController(service);
	}

	@DisplayName("Deve retornar um Pedido. ")
	@Test
	public void shouldReturnPedidoDTO() {
		PedidoDTO PedidoDTO = PedidoDTOProvider.criar();
		when(service.findById(Pedido_ID)).thenReturn(PedidoDTO);
		var response = controller.findById(Pedido_ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(PedidoDTO.class, response.getBody().getClass());
	}
	


}
