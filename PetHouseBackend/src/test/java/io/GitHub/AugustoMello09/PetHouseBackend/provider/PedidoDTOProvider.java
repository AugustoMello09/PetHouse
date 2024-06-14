package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;

public class PedidoDTOProvider {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public PedidoDTO criar() {
		PedidoDTO entity = new PedidoDTO();
		entity.setId(ID);
		entity.setData(LocalDate.now());
		Produto produto1 = new Produto();
		produto1.setId(1L); 
		Produto produto2 = new Produto();
		produto2.setId(2L); 
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setId(ID);
		entity.setIdUsuario(usuario.getId());
		entity.setProdutos(Arrays.asList(produto1, produto2));
		return entity;
	}
}
