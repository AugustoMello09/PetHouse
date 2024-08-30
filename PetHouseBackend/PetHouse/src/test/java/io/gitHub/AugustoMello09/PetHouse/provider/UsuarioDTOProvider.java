package io.gitHub.AugustoMello09.PetHouse.provider;

import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;

public class UsuarioDTOProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";

	public UsuarioDTO criar() {
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setId(ID);
		usuario.setNome(NOME);
		usuario.setEmail(EMAIL);
		return usuario;
	}
}
