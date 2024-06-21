package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;

public interface AuthService {
	
	Usuario authenticated();
	
	void validateSelfOrAdmin(UUID Idusuario);
	
	boolean isAuthenticated();

}
