package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CategoriaDTO;

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
