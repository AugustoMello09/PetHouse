package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;

public interface PedidoService {
	
	PedidoDTO findById(UUID id);
	
	PedidoDTO create(PedidoDTO pedidoDTO);
}
