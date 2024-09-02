package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;

public interface CarrinhoService {
	
	CarrinhoDTO findById(UUID id);

	CarrinhoDTO adicionarAoCarrinho(CarrinhoDTO carrinhoDTO);

	void removerProdutoDoCarrinho(UUID idCarrinho, Long idProduto);

}
