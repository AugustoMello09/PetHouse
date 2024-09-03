package io.gitHub.AugustoMello09.PetHouse.provider;

import java.time.LocalDate;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Pedido;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;

public class PedidoProvider {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public Pedido criar() {
		Pedido entity = new Pedido();
		entity.setId(ID);
		entity.setData(LocalDate.now());
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		entity.setUsuario(usuario);
		Carrinho carrinho = new Carrinho();
		entity.setCarrinho(carrinho);
		return entity;
	}

}
