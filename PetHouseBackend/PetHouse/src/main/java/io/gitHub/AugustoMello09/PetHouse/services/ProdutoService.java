package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;

public interface ProdutoService {
	
	ProdutoDTO findById(Long id);

	Page<ProdutoDTO> findAllPaged(Pageable page);

	ProdutoDTO create(ProdutoDTO produtoDTO);

	void updateProduto(ProdutoDTO produtoDTO, Long id);

	void deleteProduto(Long id);

	void atribuirCategoria(Long idProduto, Long idCategoria);

	List<ProdutoDTO> findByCategoriaIdOrderByNome(Long idCategoria);

	List<ProdutoDTO> findByNomeContaining(String nome);

}
