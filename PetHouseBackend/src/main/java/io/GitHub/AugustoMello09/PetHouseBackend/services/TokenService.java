package io.GitHub.AugustoMello09.PetHouseBackend.services;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.TokenResponseDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;

public interface TokenService {
	
	TokenResponseDTO generateTokens(Usuario usuario);
	 
	 String validacaoToken(String token);
	 
	 TokenResponseDTO refreshAccessToken(String refreshToken);
	 

}
