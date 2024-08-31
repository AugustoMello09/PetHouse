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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.PetHouse.provider.CargoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.services.CargoService;

@ExtendWith(MockitoExtension.class)
public class CargoControllerTest {
	
	@Mock
	private CargoService service;

	@InjectMocks
	private CargoController controller;

	private static final long ID = 1L;

	private CargoDTOProvider cargoDTOProvider;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		cargoDTOProvider = new CargoDTOProvider();
		controller = new CargoController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		objectMapper = new ObjectMapper();
	}

	@DisplayName("Deve retornar um Cargo. ")
	@Test
	public void shouldControllerReturnFindById() {
		CargoDTO cargoDTO = cargoDTOProvider.criar();
		when(service.findById(ID)).thenReturn(cargoDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(CargoDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve criar um Cargo. ")
	@Test
	public void shouldReturnCreatedCargoDTOOnController() throws Exception {
		CargoDTO cargoDTO = cargoDTOProvider.criar();
		when(service.create(any(CargoDTO.class))).thenReturn(cargoDTO);
		mockMvc.perform(post("/v1/cargo/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cargoDTO))).andExpect(status().isCreated());
	}

	@DisplayName("Deve atualizar o cargo. ")
	@Test
	public void shouldReturnUpdateCargoDTOOnController() {
		CargoDTO cargoDTO = cargoDTOProvider.criar();
		doNothing().when(service).updateCargo(cargoDTO, ID);
		ResponseEntity<Void> response = controller.update(cargoDTO, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).updateCargo(cargoDTO, ID);
	}

	@DisplayName("Deve retornar uma Lista de cargos. ")
	@Test
	public void shouldReturnPagedCargoDTO() {
		List<CargoDTO> cargos = new ArrayList<>();
		when(service.listAll()).thenReturn(cargos);
		ResponseEntity<List<CargoDTO>> response = controller.findAll();
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service).listAll();
	}

	@DisplayName("Deve deletar um cargo. ")
	@Test
	public void shouldDeleteCargoWithSuccess() {
		doNothing().when(service).deleteCargo(ID);
		ResponseEntity<Void> response = controller.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service, times(1)).deleteCargo(ID);
	}


}
