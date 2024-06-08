package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CargoDTO;

public interface CargoService {
	
	CargoDTO findById(Long id);
	
	List<CargoDTO> listAll();
	
	CargoDTO create(CargoDTO cargoDTO);
	
	void updateCargo(CargoDTO cargoDTO, Long id);
	
	void deleteCargo(Long id);

}
