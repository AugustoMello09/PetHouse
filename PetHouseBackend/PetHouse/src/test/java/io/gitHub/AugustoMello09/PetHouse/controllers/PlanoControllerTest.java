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

import java.util.Collections;
import java.util.UUID;

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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PlanoDTO;
import io.gitHub.AugustoMello09.PetHouse.provider.PlanoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.PlanoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlanoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;
	
	@InjectMocks
	private PlanoVeterinarioController controller;
	
	@Mock
	private PlanoServiceImpl service;

	private static final long ID = 1L;

	private static final UUID IDUSUARIO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	private PlanoDTOProvider planoDTOProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		planoDTOProvider = new PlanoDTOProvider();
		controller = new PlanoVeterinarioController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		objectMapper = new ObjectMapper();
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
	public void shouldReturnCreatedPlanoDTOOnController() throws Exception {
		PlanoDTO planoDTO = planoDTOProvider.criar();
		when(service.create(any(PlanoDTO.class))).thenReturn(planoDTO);
		mockMvc.perform(post("/v1/plano/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(planoDTO))).andExpect(status().isCreated());	
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
		Pageable pageable = PageRequest.of(0, 10); 
		Page<PlanoDTO> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
		when(service.listAll(pageable)).thenReturn(page);
		ResponseEntity<Page<PlanoDTO>> response = controller.listAll(pageable);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(page, response.getBody());
		verify(service).listAll(pageable);
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
