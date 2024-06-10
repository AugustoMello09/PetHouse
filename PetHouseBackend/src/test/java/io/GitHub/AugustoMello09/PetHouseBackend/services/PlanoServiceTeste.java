package io.GitHub.AugustoMello09.PetHouseBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PlanoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.PlanoVeterinario;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.enums.Plano;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.PlanoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.PlanoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.PlanoVeterinarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.DataIntegratyViolationException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class PlanoServiceTeste {
	
	private static final long ID = 1L;
	
	@InjectMocks
	private PlanoVeterinarioServiceImpl service;
	
	@Mock
	private PlanoVeterinarioRepository repository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ModelMapper modelMapper;

	private PlanoProvider planoProvider;
	private PlanoDTOProvider planoDTOProvider;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new PlanoVeterinarioServiceImpl(repository, modelMapper, usuarioRepository);
		planoProvider = new PlanoProvider();
		planoDTOProvider = new PlanoDTOProvider();
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
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar uma lista de planos.")
	@Test
	public void whenFindAllThenReturnPlanoDTO() {
		List<PlanoVeterinario> planos = Arrays.asList(new PlanoVeterinario(ID, "Básico", new BigDecimal(25.00), "Plano básico preco bom", Plano.BASICO, null));
		when(repository.findAll()).thenReturn(planos);
		List<PlanoDTO> result = service.listAll();
		assertNotNull(result);
	}

	@DisplayName("Deve criar um plano com sucesso.")
	@Test
	public void whenCreateThenReturnPlanoDTO() {
		PlanoVeterinario planoEntity = planoProvider.criar();
		PlanoDTO planoDTOExpected = planoDTOProvider.criar();

		when(repository.save(any(PlanoVeterinario.class))).thenReturn(planoEntity);
		when(modelMapper.map(planoEntity, PlanoDTO.class)).thenReturn(planoDTOExpected);

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
	
	@Test
	void whenDeletePlanoWithAssociatedDataThenThrowDataIntegrityViolationException() {
		PlanoVeterinario plano = planoProvider.criar();
	    when(repository.findById(ID)).thenReturn(Optional.of(plano));
	    doThrow(DataIntegrityViolationException.class).when(repository).deleteById(ID);
	    assertThrows(DataIntegratyViolationException.class, () -> service.deletePlano(ID));
	}

}
