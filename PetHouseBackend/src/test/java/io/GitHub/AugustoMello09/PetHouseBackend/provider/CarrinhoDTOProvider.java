package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;

public class CarrinhoDTOProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public CarrinhoDTO criar() {
		CarrinhoDTO carrinho = new CarrinhoDTO();
		carrinho.setId(ID);
		return carrinho;
	}
}
