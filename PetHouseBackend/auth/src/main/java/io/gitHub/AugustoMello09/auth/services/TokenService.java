package io.gitHub.AugustoMello09.auth.services;

import io.gitHub.AugustoMello09.auth.domain.dtos.TokenResponseDTO;
import io.gitHub.AugustoMello09.auth.domain.entities.Usuario;

public interface TokenService {
	
	TokenResponseDTO generateTokens(Usuario usuario);
	 
	 String validacaoToken(String token);
	 
	 TokenResponseDTO refreshAccessToken(String refreshToken);

}
