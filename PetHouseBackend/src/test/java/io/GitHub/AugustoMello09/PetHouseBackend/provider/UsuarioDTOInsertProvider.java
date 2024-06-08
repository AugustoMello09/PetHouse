package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTOInsert;

public class UsuarioDTOInsertProvider {
	
	private static final String SENHA = "123";

	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";
	
	public UsuarioDTOInsert criar() {
        UsuarioDTOInsert usuarioDTOInsert = new UsuarioDTOInsert();
        usuarioDTOInsert.setNome(NOME);
        usuarioDTOInsert.setEmail(EMAIL);
        usuarioDTOInsert.setSenha(SENHA);
        return usuarioDTOInsert;
    }


}
