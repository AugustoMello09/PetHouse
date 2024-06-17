package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;

public class CarrinhoProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public Carrinho criar() {
		Carrinho carrinho = new Carrinho();
		carrinho.setId(ID);
		return carrinho;
	}
}
