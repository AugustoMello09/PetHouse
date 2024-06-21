package io.GitHub.AugustoMello09.PetHouseBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CargoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Cargo;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CargoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CargoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CargoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.serviceImpl.CargoServiceImpl;

@SpringBootTest
public class CargoServiceTeste {

	private static final long ID = 1L;

	@Mock
	private CargoRepository repository;

	@InjectMocks
	private CargoServiceImpl service;

	@Mock
	private ModelMapper modelMapper;

	private CargoProvider cargoProvider;
	private CargoDTOProvider cargoDTOProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CargoServiceImpl(repository, modelMapper);
		cargoProvider = new CargoProvider();
		cargoDTOProvider = new CargoDTOProvider();
	}

	@DisplayName("Deve retornar um Cargo com sucesso.")
	@Test
	public void shouldReturnACargoWithSuccess() {
		Cargo cargoEntity = cargoProvider.criar();
		CargoDTO cargoDTOExpected = cargoDTOProvider.criar();

		when(repository.findById(ID)).thenReturn(Optional.of(cargoEntity));
		when(modelMapper.map(cargoEntity, CargoDTO.class)).thenReturn(cargoDTOExpected);

		var response = service.findById(ID);
		assertNotNull(response);
		assertEquals(CargoDTO.class, response.getClass());
		assertEquals(ID, response.getId());
	}

	@DisplayName("Deve retornar Cargo não encontrado.")
	@Test
	public void shouldReturnRoleNotFound() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.findById(ID));
	}

	@DisplayName("Deve retornar uma lista de Cargo.")
	@Test
	public void whenFindAllThenReturnCargoDTO() {
		List<Cargo> cargos = Arrays.asList(new Cargo(ID, "ROLE_TESTE"));
		when(repository.findAll()).thenReturn(cargos);
		List<CargoDTO> result = service.listAll();
		assertNotNull(result);
	}

	@DisplayName("Deve criar um cargo com sucesso.")
	@Test
	public void whenCreateThenReturnCargoDTO() {
		Cargo cargoEntity = cargoProvider.criar();
		CargoDTO cargoDTOExpected = cargoDTOProvider.criar();

		when(repository.save(any(Cargo.class))).thenReturn(cargoEntity);
		when(modelMapper.map(cargoEntity, CargoDTO.class)).thenReturn(cargoDTOExpected);

		service.create(cargoDTOExpected);

		verify(repository, times(1)).save(any(Cargo.class));
	}

	@DisplayName("Atualização Deve retornar cargo não encontrado.")
	@Test
	public void shouldUpdateReturnRoleNotFound() {
		CargoDTO cargoDTO = cargoDTOProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.updateCargo(cargoDTO, ID));
	}

	@DisplayName("Atualização Deve retornar sucesso.")
	@Test
	public void shouldUpdateReturnSuccess() {
		CargoDTO cargoDTO = cargoDTOProvider.criar();
		Cargo cargo = cargoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(cargo));
		when(repository.save(any(Cargo.class))).thenReturn(cargo);
		when(modelMapper.map(cargo, CargoDTO.class)).thenReturn(cargoDTO);
		service.updateCargo(cargoDTO, ID);
		verify(repository, times(1)).findById(ID);
		verify(repository, times(1)).save(any(Cargo.class));
	}

	@DisplayName("Deve deletar um cargo com sucesso.")
	@Test
	public void shouldRoleWithSuccess() {
		Cargo cargo = cargoProvider.criar();
		when(repository.findById(ID)).thenReturn(Optional.of(cargo));
		service.deleteCargo(ID);
	}

	@DisplayName("Deve não encontrar um cargo ao deletar.")
	@Test
	public void shouldReturnRoleNotFoundWhenDelete() {
		when(repository.findById(ID)).thenReturn(Optional.empty());
		assertThrows(ObjectNotFoundException.class, () -> service.deleteCargo(ID));
	}

}
