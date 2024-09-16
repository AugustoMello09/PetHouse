package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;

public interface AuthService {
	
	Usuario authenticated();

	void validateSelfOrAdmin(UUID Idusuario);

	boolean isAuthenticated();
}
