package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;
import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PlanoDTO;

public interface PlanoVeterinarioService {

	PlanoDTO findById(Long id);
	
	List<PlanoDTO> listAll();
	
	PlanoDTO create(PlanoDTO planoDTO);
	
	void updatePlano(PlanoDTO planoDTO, Long id);
	
	void deletePlano(Long id);
	
	void getPlan(UUID IdUsuario, Long idPlano);
	
	void removePlan(UUID IdUsuario, Long idPlano);
}
