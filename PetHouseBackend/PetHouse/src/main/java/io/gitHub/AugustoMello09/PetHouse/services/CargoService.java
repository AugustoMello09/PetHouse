package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.List;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CargoDTO;

public interface CargoService {
	
	CargoDTO findById(Long id);

	List<CargoDTO> listAll();

	CargoDTO create(CargoDTO cargoDTO);

	void updateCargo(CargoDTO cargoDTO, Long id);

	void deleteCargo(Long id);
}
