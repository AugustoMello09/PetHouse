package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.List;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CategoriaDTO;

public interface CategoriaService {
	
	CategoriaDTO findById(Long id);

	List<CategoriaDTO> listAll();

	CategoriaDTO create(CategoriaDTO categoriaDTO);

	void updateCategoria(CategoriaDTO categoriaDTO, Long id);

	void deleteCategoria(Long id);

}
