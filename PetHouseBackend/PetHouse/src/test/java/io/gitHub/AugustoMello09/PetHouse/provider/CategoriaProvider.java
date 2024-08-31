package io.gitHub.AugustoMello09.PetHouse.provider;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Categoria;

public class CategoriaProvider {
	
	private static final long ID = 1L;
	private static final String NOME = "Brinquedos";

	public Categoria criar() {
		Categoria categoria = new Categoria();
		categoria.setId(ID);
		categoria.setNomeCategoria(NOME);
		return categoria;
	}

}
