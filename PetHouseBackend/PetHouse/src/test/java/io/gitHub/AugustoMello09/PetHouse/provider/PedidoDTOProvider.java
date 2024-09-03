package io.gitHub.AugustoMello09.PetHouse.provider;

import java.time.LocalDate;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PedidoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;

public class PedidoDTOProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public PedidoDTO criar() {
		PedidoDTO entity = new PedidoDTO();
		entity.setId(ID);
		entity.setData(LocalDate.now());
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setId(ID);
		entity.setIdUsuario(usuario.getId());
		return entity;
	}

}
