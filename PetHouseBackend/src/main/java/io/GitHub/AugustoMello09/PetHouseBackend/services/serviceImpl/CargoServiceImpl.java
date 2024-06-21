package io.GitHub.AugustoMello09.PetHouseBackend.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CargoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Cargo;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CargoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.CargoService;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class CargoServiceImpl implements CargoService{
	
	private final CargoRepository repository;
	
	private final ModelMapper mapper;

	public CargoServiceImpl(CargoRepository repository, ModelMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}
	
	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	@Transactional(readOnly = true)
	public CargoDTO findById(Long id) {
		Optional<Cargo> entity = repository.findById(id);
		Cargo cargo = entity.orElseThrow(() -> new  ObjectNotFoundException("Cargo não encontrado"));
		return mapper.map(cargo, CargoDTO.class);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	@Transactional(readOnly = true)
	public List<CargoDTO> listAll() {
		List<Cargo> cargos = repository.findAll();
		return cargos.stream().map(x -> mapper.map(x, CargoDTO.class)).collect(Collectors.toList());
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	@Transactional
	public CargoDTO create(CargoDTO cargoDTO) {
		Cargo cargo = new Cargo();
		cargo.setAuthority(cargoDTO.getAuthority());
		repository.save(cargo);
		return mapper.map(cargo, CargoDTO.class);
	}

	@Override 
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	@Transactional
	public void updateCargo(CargoDTO cargoDTO, Long id) {
		Cargo cargo	= repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
		cargo.setAuthority(cargoDTO.getAuthority());
		repository.save(cargo);
		mapper.map(cargo, CargoDTO.class);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	@Transactional
	public void deleteCargo(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	
	

}
