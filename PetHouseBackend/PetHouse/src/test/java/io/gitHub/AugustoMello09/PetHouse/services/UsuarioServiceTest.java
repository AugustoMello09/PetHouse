package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import io.gitHub.AugustoMello09.PetHouse.provider.UsuarioDTOInsertProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.UsuarioDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.UsuarioProvider;
import io.gitHub.AugustoMello09.PetHouse.repositories.CargoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.CarrinhoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.UsuarioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	private static final long IDCARGO = 1L;
	
	@Mock
	private UsuarioRepository repository;

	@InjectMocks
	private UsuarioServiceImpl service;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;
	
	@Mock
	private CargoRepository cargoRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private CarrinhoRepository carrinhoRepository;
	
	private UsuarioProvider usuarioProvider;
	private UsuarioDTOProvider usuarioDTOProvider;
	private UsuarioDTOInsertProvider usuarioDTOInsertProvider;

	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		usuarioProvider = new UsuarioProvider(passwordEncoder);
		usuarioDTOProvider = new UsuarioDTOProvider();
		usuarioDTOInsertProvider = new UsuarioDTOInsertProvider();
		service = new UsuarioServiceImpl(repository, cargoRepository, passwordEncoder, carrinhoRepository, modelMapper);
	}
	
	@DisplayName("Deve retornar um Usuario com sucesso.")
	@Test
	public void shouldReturnAUserWithSuccess() {
		Usuario usuario = usuarioProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(usuario));

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(UsuarioDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}
	
	@DisplayName("Deve retornar Usuario não encontrado.")
	@Test
	public void shouldReturnUserNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}
	
	@DisplayName("Deve retornar paginação de usuários.")
	@Test
	public void whenFindAllPagedThenReturnPageOfUsuarioDTO() {
		Carrinho carrinho = new Carrinho();
		carrinho.setId(UUID.randomUUID());
		List<Usuario> usu = Arrays.asList(new Usuario(ID, "TEste", "Email@email.com", "senha", carrinho));
		Page<Usuario> fillPage = new PageImpl<>(usu);
		
		when(repository.findAll(any(Pageable.class))).thenReturn(fillPage);
		
		Page<UsuarioDTO> result = service.findAllPaged(PageRequest.of(0, 5));
		assertNotNull(result);
	}
	
	@DisplayName("Deve criar um usuário com sucesso.")
	@Test
	public void whenCreateThenReturnUsuarioDTO() {
        UsuarioDTOInsert usuarioDTOInsert = usuarioDTOInsertProvider.criar();
		Usuario usuarioEntity = usuarioProvider.criar();
		

		when(repository.save(any(Usuario.class))).thenReturn(usuarioEntity);
		
		
		service.createUser(usuarioDTOInsert);

		verify(repository, times(1)).save(any(Usuario.class));
	}
	
	@DisplayName("Deve retornar Email já existe.")
	@Test
	public void shouldReturnDataIntegratyViolationExceptionWhenEmailExist() {
		UsuarioDTO usuarioDTOExpected = usuarioDTOProvider.criar();
	    UUID differentUserId = UUID.fromString("248cf4fc-b379-4e25-8bf4-f73feb06befa"); 
	    Usuario usuarioEntity = new Usuario(differentUserId, "Carlos", "meuEmail@gmail.com", "123", null);

	    when(repository.findByEmail(usuarioDTOExpected.getEmail()))
	      .thenReturn(Optional.of(usuarioEntity));

	    DataIntegratyViolationException exception = assertThrows(DataIntegratyViolationException.class, () -> {
	        service.emailAlreadyExists(usuarioDTOExpected);
	    });

	    assertEquals("Email já existe", exception.getMessage());
	}
	
	@DisplayName("Não deve lançar exceção quando o e-mail não existe")
	@Test
	public void shouldNotThrowExceptionWhenEmailNotExists() {
		Usuario usuario = usuarioProvider.criar();
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		
		when(repository.findByEmail(usuarioDTO.getEmail())).thenReturn(Optional.of(usuario));
		
		service.emailAlreadyExists(usuarioDTO);
	}
	
	@DisplayName("Atualização Deve retornar usuário não encontrado.")
	@Test
	public void shouldUpdateReturnUserNotFound() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		
		when(repository.findById(ID)).thenReturn(Optional.empty());
		
		assertThrows(ObjectNotFoundException.class, () -> service.updateUser(usuarioDTO, ID));
	}
	
	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		Usuario usuario = usuarioProvider.criar();
		
		when(repository.findById(ID)).thenReturn(Optional.of(usuario));
		when(repository.save(any(Usuario.class))).thenReturn(usuario);
		
		service.updateUser(usuarioDTO, ID);
		
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Usuario.class));
	}
	
	@DisplayName("Deve deletar um usuário com sucesso.")
	@Test
	public void shouldDeleteWithSuccess() {
		Usuario usuario = usuarioProvider.criar();
		
		when(repository.findById(ID)).thenReturn(Optional.of(usuario));
		
		service.deleteUser(ID);
	}

	@DisplayName("Deve não encontrar um usuário ao deletar.")
	@Test
	public void shouldReturnUserNotFoundWhenDelete() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.deleteUser(ID));
	}
	
	@DisplayName("Deve não encontrar um usuário ao associar Cargo.")
	@Test
	public void shouldReturnUserNotFoundWhenRole() {
		UsuarioDTO userDto = new UsuarioDTO();
		userDto.setId(ID);
		userDto.setNome("John Doe");
		userDto.setEmail("john.doe@example.com");

		Cargo roleDto = new Cargo();
		roleDto.setId(IDCARGO);
		userDto.getCargos().add(roleDto);
		
		Usuario usuario = usuarioProvider.criar();
		
		when(cargoRepository.findById(roleDto.getId())).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.atribuirCargo(usuario, userDto);
		});
		assertEquals("Cargo não encontrado", exception.getMessage());
	}
	
	@DisplayName("deve não encontrar um usuário ao atribuir um cargo")
	@Test
	public void shouldAtribuirCargoUserNotFound() {
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.atribuirCargo(usuarioDTO, ID);
		});
		assertEquals("Usuário não encontrado", exception.getMessage());
	}
	
	@DisplayName("deve atribuir um cargo com sucesso")
	@Test
	public void shouldAtribuirCargo() {
		Usuario usuario = usuarioProvider.criar();
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();
		
		when(repository.findById(ID)).thenReturn(Optional.of(usuario));
		when(repository.save(any(Usuario.class))).thenReturn(usuario);
		
		service.atribuirCargo(usuarioDTO, ID);
	
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Usuario.class));
	}
	
	@DisplayName("Deve lançar exceção Cargo não encontrado ao tentar atribuir um cargo inexistente")
	@Test
	public void shouldThrowExceptionWhenRoleNotFound() {
	    Usuario usuario = usuarioProvider.criar(); 
	    UsuarioDTO usuarioDTO = usuarioDTOProvider.criar(); 
	    
	    Cargo cargo = new Cargo();
	    cargo.setId(IDCARGO);
	    List<Cargo> cargosList = new ArrayList<>();
	    cargosList.add(cargo);
	    usuarioDTO.setCargos(cargosList);


	    when(cargoRepository.findById(cargo.getId())).thenReturn(Optional.empty());

	
	    ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
	        service.atribuirCargo(usuario, usuarioDTO);
	    });


	    assertEquals("Cargo não encontrado", exception.getMessage());
	    verify(cargoRepository, times(1)).findById(cargo.getId());
	}


}
