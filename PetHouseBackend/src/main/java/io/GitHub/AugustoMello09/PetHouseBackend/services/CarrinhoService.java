package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;

public interface CarrinhoService {
	
	CarrinhoDTO findById(UUID id);
	
	void adicionarAoCarrinho(CarrinhoDTO carrinhoDTO);
	
	void removerProdutoDoCarrinho(UUID idCarrinho, Long idProduto);

}
