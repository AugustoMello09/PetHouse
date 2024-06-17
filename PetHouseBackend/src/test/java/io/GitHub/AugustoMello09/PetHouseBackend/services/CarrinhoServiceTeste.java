package io.GitHub.AugustoMello09.PetHouseBackend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CarrinhoDTOProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.provider.CarrinhoProvider;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CarrinhoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class CarrinhoServiceTeste {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	@InjectMocks
	private CarrinhoServiceImpl service;

	@Mock
	private CarrinhoRepository repository;

	@Mock
	private ModelMapper mapper;

	private CarrinhoProvider carrinhoProvider;
	private CarrinhoDTOProvider carrinhoDTOPrivider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		service = new CarrinhoServiceImpl(repository, mapper);
		carrinhoDTOPrivider = new CarrinhoDTOProvider();
		carrinhoProvider = new CarrinhoProvider();
	}

	@DisplayName("Deve retornar um carrinho")
	@Test
	public void shouldReturnCarrinhoDTO() {
		Carrinho carrinho = carrinhoProvider.criar();
		CarrinhoDTO carrinhoDTO = carrinhoDTOPrivider.criar();
		when(repository.findById(any(UUID.class))).thenReturn(Optional.of(carrinho));
		when(mapper.map(carrinho, CarrinhoDTO.class)).thenReturn(carrinhoDTO);
		service.findById(ID);
		verify(repository, times(1)).findById(any(UUID.class));
	}

	@DisplayName("Deve retornar carrinho não encontrado")
	@Test
	public void shouldReturnCarrinhoNotFound() {
		when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

		ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
			service.findById(any(UUID.class));
		});
		assertEquals("Carrinho não encontrado", exception.getMessage());
	}

}
