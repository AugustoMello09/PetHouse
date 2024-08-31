package io.gitHub.AugustoMello09.PetHouse.provider;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CategoriaDTO;

public class CategoriaDTOProvider {
	
	private static final long ID = 1L;
	private static final String NOME = "Brinquedos";

	public CategoriaDTO criar() {
		CategoriaDTO categoria = new CategoriaDTO();
		categoria.setId(ID);
		categoria.setNomeCategoria(NOME);
		return categoria;
	}
}
