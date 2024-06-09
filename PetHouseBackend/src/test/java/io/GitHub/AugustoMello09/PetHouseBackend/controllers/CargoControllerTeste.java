package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CargoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CargoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.services.CargoService;

@SpringBootTest
public class CargoControllerTeste {
	
	@Mock
	private CargoService service;
	
	@InjectMocks
	private CargoController controller; 
	
	private static final long ID = 1L;

	private CargoDTOProvider cargoDTOProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		cargoDTOProvider = new CargoDTOProvider();
		controller = new CargoController(service);
	}
	
	@DisplayName("Deve retornar um usuário. ")
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

	@DisplayName("Deve criar um usuário. ")
	@Test
	public void shouldReturnCreatedClienteDTOOnController() {
		CargoDTO cargoDTO = cargoDTOProvider.criar();
		when(service.create(cargoDTO)).thenReturn(cargoDTO);
		var response = controller.create(cargoDTO);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(CargoDTO.class, response.getBody().getClass());
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
	
	
	@DisplayName("Deve retornar uma paginação de cargos. ")
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
		verify(service, times(1)).deleteCargo(ID);;
	}

}
