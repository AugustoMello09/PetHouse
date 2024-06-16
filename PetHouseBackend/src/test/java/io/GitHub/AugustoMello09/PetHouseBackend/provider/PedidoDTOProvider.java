package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.ProdutoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTO;

public class PedidoDTOProvider {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public PedidoDTO criar() {
		PedidoDTO entity = new PedidoDTO();
		entity.setId(ID);
		entity.setData(LocalDate.now());
		ProdutoDTO produto1 = new ProdutoDTO();
		produto1.setId(1L); 
		ProdutoDTO produto2 = new ProdutoDTO();
		produto2.setId(2L); 
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setId(ID);
		entity.setIdUsuario(usuario.getId());
		entity.setProdutos(Arrays.asList(produto1, produto2));
		return entity;
	}
}
