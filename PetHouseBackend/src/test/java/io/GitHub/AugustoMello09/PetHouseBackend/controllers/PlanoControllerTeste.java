package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PlanoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.PlanoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.services.PlanoVeterinarioServiceImpl;

@SpringBootTest
public class PlanoControllerTeste {
	
	@InjectMocks
	private PlanoVeterinarioController controller;
	
	@Mock
	private PlanoVeterinarioServiceImpl service;

	private static final long ID = 1L;

	private static final UUID IDUSUARIO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	private PlanoDTOProvider planoDTOProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		planoDTOProvider = new PlanoDTOProvider();
		controller = new PlanoVeterinarioController(service);
	}
	
	@DisplayName("Deve retornar um Plano. ")
	@Test
	public void shouldControllerReturnFindById() {
		PlanoDTO planoDTO = planoDTOProvider.criar();
		when(service.findById(ID)).thenReturn(planoDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(PlanoDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve criar um Plano. ")
	@Test
	public void shouldReturnCreatedPlanoDTOOnController() {
		PlanoDTO planoDTO = planoDTOProvider.criar();
		when(service.create(planoDTO)).thenReturn(planoDTO);
		var response = controller.crate(planoDTO);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(PlanoDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve atualizar o plano. ")
	@Test
	public void shouldReturnUpdatePlanoDTOOnController() {
		PlanoDTO planoDTO = planoDTOProvider.criar();
		doNothing().when(service).updatePlano(planoDTO, ID);
		ResponseEntity<Void> response = controller.update(planoDTO, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).updatePlano(planoDTO, ID);
	}
	
	
	@DisplayName("Deve retornar uma Lista de planos. ")
	@Test
	public void shouldReturnListPlanoDTO() {
		List<PlanoDTO> planos = new ArrayList<>();
		when(service.listAll()).thenReturn(planos);
		ResponseEntity<List<PlanoDTO>> response = controller.listAll();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service).listAll();
	}
	
	@DisplayName("Deve deletar um plano. ")
	@Test
	public void shouldDeletePlanoWithSuccess() {
		doNothing().when(service).deletePlano(ID);
		ResponseEntity<Void> response = controller.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service, times(1)).deletePlano(ID);;
	}
	
	@DisplayName("Deve contratar um plano. ")
	@Test
	public void shouldGetPlanoWithSuccess() {
		doNothing().when(service).getPlan(IDUSUARIO, ID);
		ResponseEntity<Void> response = controller.getPlan(ID, IDUSUARIO);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).getPlan(IDUSUARIO, ID);
	}
	
	@DisplayName("Deve remover um plano. ")
	@Test
	public void shouldRemovePlanoWithSuccess() {
		doNothing().when(service).removePlan(IDUSUARIO, ID);
		ResponseEntity<Void> response = controller.removePlan(ID, IDUSUARIO);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).removePlan(IDUSUARIO, ID);
	}
	
}
