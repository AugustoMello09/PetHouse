package io.gitHub.AugustoMello09.PetHouse.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PlanoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.PlanoVeterinario;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Plano;
import io.gitHub.AugustoMello09.PetHouse.provider.PlanoDTOProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.PlanoProvider;
import io.gitHub.AugustoMello09.PetHouse.provider.UsuarioProvider;
import io.gitHub.AugustoMello09.PetHouse.repositories.PlanoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import io.gitHub.AugustoMello09.PetHouse.services.serviceImpl.PlanoServiceImpl;

@ExtendWith(MockitoExtension.class)
public class PlanoServiceTest {
	
	private static final long ID = 1L;

	private static final UUID IDUSUARIO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@InjectMocks
	private PlanoServiceImpl service;

	@Mock
	private PlanoRepository repository;

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private ModelMapper modelMapper;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	private PlanoProvider planoProvider;
	private PlanoDTOProvider planoDTOProvider;
	private UsuarioProvider usuarioProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new PlanoServiceImpl(repository, modelMapper, usuarioRepository);
		planoProvider = new PlanoProvider();
		planoDTOProvider = new PlanoDTOProvider();
		usuarioProvider = new UsuarioProvider(passwordEncoder);
	}

	@DisplayName("Deve retornar um Plano com sucesso.")
	@Test
	public void shouldReturnAPlanoWithSuccess() {
		PlanoVeterinario planoEntity = planoProvider.criar();
		PlanoDTO planoDTOExpected = planoDTOProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(planoEntity));
		when(modelMapper.map(planoEntity, PlanoDTO.class)).thenReturn(planoDTOExpected);

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(PlanoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@DisplayName("Deve retornar Plano não encontrado.")
	@Test
	public void shouldReturnPlanoNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById(ID);
		});
		assertEquals("Plano não encontrado", exception.getMessage());
	}

	@DisplayName("Deve retornar uma lista de planos.")
	@Test
	public void whenFindAllThenReturnPlanoDTO() {
		List<PlanoVeterinario> planos = Arrays.asList(new PlanoVeterinario(ID, "Básico", new BigDecimal(25.00),
				"Plano básico preco bom", Plano.BASICO, null));
		Page<PlanoVeterinario> praPage = new PageImpl<>(planos);
		when(repository.findAll(any(Pageable.class))).thenReturn(praPage);
		Page<PlanoDTO> result = service.listAll(PageRequest.of(0, 5));
		assertNotNull(result);
	}

	@DisplayName("Deve criar um plano com sucesso.")
	@Test
	public void whenCreateThenReturnPlanoDTO() {
		PlanoVeterinario planoEntity = planoProvider.criar();
		PlanoDTO planoDTOExpected = planoDTOProvider.criar();

		when(repository.save(any(PlanoVeterinario.class))).thenReturn(planoEntity);
		when(modelMapper.map(any(PlanoVeterinario.class), eq(PlanoDTO.class))).thenReturn(planoDTOExpected);

		service.create(planoDTOExpected);

		verify(repository, times(1)).save(any(PlanoVeterinario.class));
	}

	@DisplayName("Atualização Deve retornar plano não encontrado.")
	@Test
	public void shouldUpdateReturnPlanoNotFound() {
		PlanoDTO planoDTO = planoDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.updatePlano(planoDTO, ID));
	}

	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {
		PlanoDTO planoDTO = planoDTOProvider.criar();
		PlanoVeterinario plano = planoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(plano));
		when(repository.save(any(PlanoVeterinario.class))).thenReturn(plano);
		when(modelMapper.map(plano, PlanoDTO.class)).thenReturn(planoDTO);
		service.updatePlano(planoDTO, ID);
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(PlanoVeterinario.class));
	}

	@DisplayName("Deve deletar um plano com sucesso.")
	@Test
	public void shouldPlanoWithSuccess() {
		PlanoVeterinario plano = planoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(plano));
		service.deletePlano(ID);
	}

	@DisplayName("Deve não encontrar um plano ao deletar.")
	@Test
	public void shouldReturnPlanoNotFoundWhenDelete() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.deletePlano(ID));
	}

	@DisplayName("Deve não deletar quando estiver usuário associados.")
	@Test
	public void whenDeletePlanoWithAssociatedDataThenThrowDataIntegrityViolationException() {
		PlanoVeterinario plano = planoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(plano));
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(ID);
		assertThrows(DataIntegratyViolationException.class, () -> service.deletePlano(ID));
	}

	@DisplayName("Deve retornar usuário não encontrado ao contratar.")
	@Test
	public void whenGetPlanShouldThrowExceptionWhenUserNotFound() {
		PlanoVeterinario plano = planoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(plano));
		when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.getPlan(IDUSUARIO, ID);
		});
		assertEquals("Usuario não encontrado", exception.getMessage());
	}

	@DisplayName("Deve retornar plano não encontrado ao contratar.")
	@Test
	public void whenGetPlanShouldThrowExceptionWhenPlanNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.getPlan(IDUSUARIO, ID);
		});
		assertEquals("Plano não encontrado", exception.getMessage());
	}

	@DisplayName("Deve associar a um usuário com sucesso.")
	@Test
	public void whenGetPlanAssociatesPlanWithUserSuccessfully() {
		Usuario usuario = usuarioProvider.criar();
		when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.of(usuario));
		PlanoVeterinario plano = planoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(plano));
		service.getPlan(IDUSUARIO, ID);
		verify(usuarioRepository, times(1)).findById(IDUSUARIO);
		verify(repository, times(1)).findById(ID);
		verify(usuarioRepository, times(1)).save(any(Usuario.class));
		verify(repository, times(1)).save(any(PlanoVeterinario.class));
	}

	@DisplayName("Deve retornar plano não encontrado ao tentar cancelar o plano.")
	@Test
	public void removePlanShouldThrowExceptionWhenPlanNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.removePlan(IDUSUARIO, ID);
		});
		assertEquals("Plano não encontrado", exception.getMessage());
	}

	@DisplayName("Deve retornar usuário não encontrado ao tentar cancelar o plano.")
	@Test
	public void removePlanShouldThrowExceptionWhenUserNotFound() {
		PlanoVeterinario plano = planoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(plano));
		when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.empty());
		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.removePlan(IDUSUARIO, ID);
		});
		assertEquals("Usuario não encontrado", exception.getMessage());
	}

	@DisplayName("Deve cancelar um plano com sucesso.")
	@Test
	public void removePlanDisassociatesPlanFromUserSuccessfully() {
		Usuario usuario = usuarioProvider.criar();
		PlanoVeterinario plano = planoProvider.criar();
		usuario.setPlano(plano);
		plano.setUsuario(usuario);


		when(usuarioRepository.findById(IDUSUARIO)).thenReturn(Optional.of(usuario));
		when(repository.findById(ID)).thenReturn(Optional.of(plano));


		service.removePlan(IDUSUARIO, ID);


		verify(usuarioRepository, times(1)).findById(IDUSUARIO);
		verify(repository, times(1)).findById(ID);
		verify(usuarioRepository, times(1)).save(usuario);
		verify(repository, times(1)).save(plano);


		assertNull(usuario.getPlano());
		assertNull(plano.getUsuario());

	}


}
