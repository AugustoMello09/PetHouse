package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CarrinhoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	private final CarrinhoRepository repository;
	private final ModelMapper mapper;

	public CarrinhoServiceImpl(CarrinhoRepository repository, ModelMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public CarrinhoDTO findById(UUID id) {
		Optional<Carrinho> entity = repository.findById(id);
		Carrinho carrinho = entity.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));
		return mapper.map(carrinho, CarrinhoDTO.class);
	}

}
