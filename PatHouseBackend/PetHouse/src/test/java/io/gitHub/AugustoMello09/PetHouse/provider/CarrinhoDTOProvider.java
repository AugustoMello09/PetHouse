package io.gitHub.AugustoMello09.PetHouse.provider;

import java.util.ArrayList;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ItemCarrinhoDTO;

public class CarrinhoDTOProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	public CarrinhoDTO criar() {
		CarrinhoDTO carrinhoDTO = new CarrinhoDTO();
		carrinhoDTO.setId(ID);
		carrinhoDTO.setItemsCarrinho(new ArrayList<>());
		ItemCarrinhoDTO itemDTO = new ItemCarrinhoDTO();
		itemDTO.setProdutoId(1L);
		carrinhoDTO.getItemsCarrinho().add(itemDTO);
		return carrinhoDTO;
	}

}
