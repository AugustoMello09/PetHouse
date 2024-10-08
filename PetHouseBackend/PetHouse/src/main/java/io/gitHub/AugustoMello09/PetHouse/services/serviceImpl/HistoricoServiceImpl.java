package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.HistoricoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Historico;
import io.gitHub.AugustoMello09.PetHouse.repositories.HistoricoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.HistoricoService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistoricoServiceImpl implements HistoricoService {

	private final HistoricoRepository repository;

	@Override
	public HistoricoDTO findById(UUID id) {
		Optional<Historico> obj = repository.findById(id);
		Historico entity = obj.orElseThrow(() -> new ObjectNotFoundException("Histórico não encontrado"));
		return new HistoricoDTO(entity);
	}

}
