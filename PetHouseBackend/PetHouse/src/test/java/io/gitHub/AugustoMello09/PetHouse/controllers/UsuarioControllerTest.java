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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.provider.UsuarioDTOInsertProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.UsuarioDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.services.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
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
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		objectMapper = new ObjectMapper();
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
	public void shouldReturnCreatedClienteDTOOnController() throws Exception  {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		UsuarioDTOInsert usuarioInsertDTO = usuarioDTOInsertProvider.criar();
		when(service.createUser(any(UsuarioDTOInsert.class))).thenReturn(usuarioDTO);
		mockMvc.perform(post("/v1/usuario/").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(usuarioInsertDTO))).andExpect(status().isCreated());
	
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
