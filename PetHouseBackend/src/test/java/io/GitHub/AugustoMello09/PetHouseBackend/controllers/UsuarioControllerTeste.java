package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;

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

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTOInsert;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.UsuarioDTOInsertProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.UsuarioDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.services.UsuarioService;

@SpringBootTest
public class UsuarioControllerTeste {
	
	@Mock
	private UsuarioService service;
	
	@InjectMocks
	private UsuarioController controller; 
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	
	private UsuarioDTOProvider usuarioDTOProvider;
	private UsuarioDTOInsertProvider usuarioDTOInsertProvider;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		usuarioDTOProvider = new UsuarioDTOProvider();
		usuarioDTOInsertProvider = new UsuarioDTOInsertProvider();
		controller = new UsuarioController(service);
	}
	
	@DisplayName("Deve retornar um usuário. ")
	@Test
	public void shouldControllerReturnFindById() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		when(service.findById(ID)).thenReturn(usuarioDTO);
		var response = controller.findById(ID);
		assertNotNull(response);
		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UsuarioDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve criar um usuário. ")
	@Test
	public void shouldReturnCreatedClienteDTOOnController() {
		UsuarioDTOInsert usuarioInsertDTO = usuarioDTOInsertProvider.criar();
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		when(service.createUser(usuarioInsertDTO)).thenReturn(usuarioDTO);
		var response = controller.create(usuarioInsertDTO);
		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(UsuarioDTO.class, response.getBody().getClass());
	}

	@DisplayName("Deve atualizar o usuário. ")
	@Test
	public void shouldReturnUpdateUsuarioDTOOnController() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		doNothing().when(service).updateUser(usuarioDTO, ID);
		ResponseEntity<Void> response = controller.update(usuarioDTO, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		verify(service, times(1)).updateUser(usuarioDTO, ID);;
	}
	
	
	@DisplayName("Deve retornar uma paginação de usuários. ")
	@Test
	public void shouldReturnPagedUsuarioDTO() {
		Pageable pageable = PageRequest.of(0, 10); 
		Page<UsuarioDTO> page = new PageImpl<>(Collections.emptyList(), pageable, 0);
		when(service.findAllPaged(pageable)).thenReturn(page);
		ResponseEntity<Page<UsuarioDTO>> response = controller.findAllPaged(pageable);
		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(page, response.getBody());
		verify(service).findAllPaged(pageable);
	}
	
	@DisplayName("Deve deletar um usuários. ")
	@Test
	public void shouldDeleteUsuarioWithSuccess() {
		doNothing().when(service).deleteUser(ID);
		ResponseEntity<Void> response = controller.delete(ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(service, times(1)).deleteUser(ID);;
	}
	
	@DisplayName("Deve atribuir um  cargo ao usuário. ")
	@Test
	public void shouldAtribuirCargo() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		doNothing().when(service).atribuirCargo(usuarioDTO, ID);
		ResponseEntity<Void> response = controller.atribuir(usuarioDTO, ID);
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
