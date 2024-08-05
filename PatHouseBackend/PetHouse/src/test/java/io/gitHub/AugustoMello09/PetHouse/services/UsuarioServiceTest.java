package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import io.gitHub.AugustoMello09.PetHouse.provider.CarrinhoDTOProvider;
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
	
	@Mock
	private UsuarioRepository repository;

	@InjectMocks
	private UsuarioServiceImpl service;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private CargoRepository cargoRepository;
	
	@Mock
	private CarrinhoRepository carrinhoRepository;
	
	private UsuarioProvider usuarioProvider;
	private UsuarioDTOProvider usuarioDTOProvider;
	private UsuarioDTOInsertProvider usuarioDTOInsertProvider;
	private CarrinhoDTOProvider carrinhoDTOProvider;

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	private static final long IDCARGO = 1L;
	
	@BeforeEach
	public void setUp() {	
		MockitoAnnotations.openMocks(this);
		usuarioProvider = new UsuarioProvider(passwordEncoder);
		usuarioDTOProvider = new UsuarioDTOProvider();
		usuarioDTOInsertProvider = new UsuarioDTOInsertProvider();
		service = new UsuarioServiceImpl(repository, passwordEncoder, modelMapper, cargoRepository, carrinhoRepository);
		carrinhoDTOProvider = new CarrinhoDTOProvider();
	}
	
	@DisplayName("Deve retornar um Usuario com sucesso.")
	@Test
	public void shouldReturnAUserWithSuccess() {
		Usuario usuario = usuarioProvider.criar();
		UsuarioDTO usuarioDTO = usuarioDTOProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(usuario));
		when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);

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
		List<Usuario> usu = Arrays.asList(new Usuario("TEste", "Email@email.com", "senha", ID,null, null));
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
		UsuarioDTO usuarioDTOExpected = usuarioDTOProvider.criar();
		CarrinhoDTO carrinhoDTO = carrinhoDTOProvider.criar();

		when(repository.save(any(Usuario.class))).thenReturn(usuarioEntity);
		when(modelMapper.map(any(Usuario.class), eq(UsuarioDTO.class))).thenReturn(usuarioDTOExpected);
		when(modelMapper.map(any(Carrinho.class), eq(CarrinhoDTO.class))).thenReturn(carrinhoDTO);
		
		service.createUser(usuarioDTOInsert);

		verify(repository, times(1)).save(any(Usuario.class));
	}

	@DisplayName("Deve retornar Email já existe.")
	@Test
	public void shouldReturnDataIntegratyViolationExceptionWhenEmailExist() {
		UsuarioDTO usuarioDTOExpected = usuarioDTOProvider.criar();
	    UUID differentUserId = UUID.fromString("248cf4fc-b379-4e25-8bf4-f73feb06befa"); 
	    Usuario usuarioEntity = new Usuario("Carlos", "meuEmail@gmail.com", "123", differentUserId,null, null);

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
		when(modelMapper.map(usuario, UsuarioDTO.class)).thenReturn(usuarioDTO);
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
	
	@DisplayName("Deve associar cargos.")
	@Test
	public void shoulAssignPositionWithSuccess() {
		
		UsuarioDTO userDto = new UsuarioDTO();
		userDto.setId(ID);
		userDto.setNome("John Doe");
		userDto.setEmail("john.doe@example.com");

		Cargo roleDto = new Cargo();
		roleDto.setId(IDCARGO);
		userDto.getCargos().add(roleDto);

		Usuario usuario = usuarioProvider.criar();

		when(cargoRepository.findById(roleDto.getId())).thenReturn(Optional.of(new Cargo()));

		service.atribuirCargo(usuario, userDto);

		verify(cargoRepository, times(1)).findById(roleDto.getId());

		assertEquals(1, usuario.getCargos().size());
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

}
