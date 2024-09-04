package io.gitHub.AugustoMello09.PetHouse.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PlanoDTO;

public interface PlanoService {
	
	PlanoDTO findById(Long id);

	Page<PlanoDTO> listAll(Pageable page);

	PlanoDTO create(PlanoDTO planoDTO);

	void updatePlano(PlanoDTO planoDTO, Long id);

	void deletePlano(Long id);


}
