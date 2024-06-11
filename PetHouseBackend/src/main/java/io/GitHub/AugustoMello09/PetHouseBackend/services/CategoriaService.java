package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CategoriaDTO;

public interface CategoriaService {
	
	CategoriaDTO findById(Long id);
	
	List<CategoriaDTO> listAll();
	
	CategoriaDTO create(CategoriaDTO categoriaDTO);
	
	void updateCategoria(CategoriaDTO categoriaDTO, Long id);
	
	void deleteCategoria(Long id);

}
