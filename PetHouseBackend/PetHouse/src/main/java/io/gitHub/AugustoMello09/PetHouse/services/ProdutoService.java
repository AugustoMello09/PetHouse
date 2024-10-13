package io.gitHub.AugustoMello09.PetHouse.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

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
	
	void uploadfile(Long idProduto, MultipartFile imagem) throws IOException;

}
