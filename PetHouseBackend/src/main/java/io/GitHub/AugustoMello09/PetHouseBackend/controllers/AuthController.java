package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.AuthenticationDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.MensagemDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.RefreshTokenDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.TokenResponseDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.services.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenService tokenService;

	@PostMapping(value = "/login")
	public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDTO data) {
	    try {
	        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
	        var authentication = authenticationManager.authenticate(usernamePassword);
	        var tokens = tokenService.generateTokens((Usuario) authentication.getPrincipal());
	        return ResponseEntity.ok().body(new TokenResponseDTO(tokens.accessToken(), tokens.refreshToken()));
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MensagemDTO("Senha inválida"));
	    }
	}
	
	@PostMapping(value = "/refresh-token")
    public ResponseEntity<TokenResponseDTO> refreshToken(@Valid @RequestBody RefreshTokenDTO refreshTokenDto) {
        TokenResponseDTO tokens = tokenService.refreshAccessToken(refreshTokenDto.token());
        return ResponseEntity.ok(tokens);
    }
	
	

	
}
