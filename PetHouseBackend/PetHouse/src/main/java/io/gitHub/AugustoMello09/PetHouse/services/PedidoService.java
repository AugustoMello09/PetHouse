package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PedidoDTO;

public interface PedidoService {

	PedidoDTO findById(UUID id);
	
	 PedidoDTO create(UUID idCarrinho);

	

}
