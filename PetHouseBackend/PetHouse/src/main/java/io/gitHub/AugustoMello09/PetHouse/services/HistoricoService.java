package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.HistoricoDTO;

public interface HistoricoService {
	
	HistoricoDTO findById(UUID id);
}
