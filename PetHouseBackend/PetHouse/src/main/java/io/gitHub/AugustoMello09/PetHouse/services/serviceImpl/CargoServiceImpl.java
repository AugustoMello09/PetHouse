package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.repositories.CargoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.CargoService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CargoServiceImpl implements CargoService {
	
	private final CargoRepository repository;
	private final ModelMapper mapper;
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public CargoDTO findById(Long id) {
		Optional<Cargo> cargo = repository.findById(id);
		Cargo entity = cargo.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
		return mapper.map(entity, CargoDTO.class);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public List<CargoDTO> listAll() {
		List<Cargo> cargo = repository.findAll();
		return cargo.stream().map(x -> mapper.map(x, CargoDTO.class)).collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public CargoDTO create(CargoDTO cargoDTO) {
		Cargo cargo = new Cargo();
		cargo.setAuthority(cargoDTO.getAuthority());
		repository.save(cargo);
		return mapper.map(cargo, CargoDTO.class);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public void updateCargo(CargoDTO cargoDTO, Long id) {
		Cargo cargo	= repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
		cargo.setAuthority(cargoDTO.getAuthority());
		repository.save(cargo);
		mapper.map(cargo, CargoDTO.class);	
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public void deleteCargo(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	

}
