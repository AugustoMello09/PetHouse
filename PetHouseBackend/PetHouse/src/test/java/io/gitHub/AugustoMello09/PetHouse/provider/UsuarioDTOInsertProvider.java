package io.gitHub.AugustoMello09.PetHouse.provider;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;

public class UsuarioDTOInsertProvider {
	
	private static final String SENHA = "123";

	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";
	
	private static final String CPFCNPJ = "63.985.026/0001-56";

	public UsuarioDTOInsert criar() {
		UsuarioDTOInsert usuarioDTOInsert = new UsuarioDTOInsert();
		usuarioDTOInsert.setNome(NOME);
		usuarioDTOInsert.setEmail(EMAIL);
		usuarioDTOInsert.setSenha(SENHA);
		usuarioDTOInsert.setCpfCnpj(CPFCNPJ);
		return usuarioDTOInsert;
	}

}
