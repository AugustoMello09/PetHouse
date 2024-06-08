package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTO;

public class UsuarioDTOProvider {

	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";

	private UsuarioDTO usuarioDTO;

	public UsuarioDTO criar() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setId(ID);
		usuario.setNome(NOME);
		usuario.setEmail(EMAIL);
		return usuario;
	}

	public UsuarioDTO retornar() {
		return usuarioDTO;
	}

}
