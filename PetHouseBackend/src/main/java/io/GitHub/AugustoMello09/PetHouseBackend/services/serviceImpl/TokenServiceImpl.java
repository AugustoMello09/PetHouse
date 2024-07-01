package io.GitHub.AugustoMello09.PetHouseBackend.services.serviceImpl;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.AuthenticationDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.TokenResponseDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Cargo;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.TokenService;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.AuthorizationException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class TokenServiceImpl implements TokenService , UserDetailsService {

	@Value("${auth.jwt.token.secret}")
	private String secret;

	@Value("${auth.jwt.token.expiration}")
	private Integer horaExpiracaoToken;

	@Value("${auth.jwt.refresh-token.expiration}")
	private Integer horaExpiracaoRefreshToken;

	private static final String ISSUER = "PetHouse";

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario entity = repository.findByEmail(email)
				.orElseThrow(() -> new ObjectNotFoundException("Email não encontrado: " + email));
		return entity;
	}
	
    public TokenResponseDTO obterToken(AuthenticationDTO authDto) {
        Usuario usuario = repository.findByEmail(authDto.email())
        		.orElseThrow(() -> new ObjectNotFoundException("Email não encontrado"));
        return generateTokens(usuario);
    }
	

	public TokenResponseDTO generateTokens(Usuario usuario) {
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        String rolesAsString = String.join(",",
	                usuario.getCargos().stream().map(Cargo::getAuthority).collect(Collectors.toList()));

	        String accessToken = JWT.create()
	                .withIssuer(ISSUER)
	                .withSubject(usuario.getEmail())
	                .withClaim("id", usuario.getId().toString())
	                .withClaim("nome", usuario.getNome())
	                .withClaim("email", usuario.getEmail())
	                .withClaim("roles", rolesAsString)
	                .withExpiresAt(genExpiInstance())
	                .sign(algorithm);

	        String refreshToken = JWT.create()
	                .withIssuer(ISSUER)
	                .withSubject(usuario.getEmail())
	                .withExpiresAt(genRefreshTokenExpiInstance())
	                .sign(algorithm);

	        return new TokenResponseDTO(accessToken, refreshToken);
	    } catch (JWTCreationException e) {
	        throw new RuntimeException("Erro na hora de gerar os tokens");
	    }
	}


	public String validacaoToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();
		} catch (JWTCreationException e) {
			throw new AuthorizationException("Problema na hora de validar o token");
		}
	}

	private Instant genExpiInstance() {
		return LocalDateTime.now().plusHours(horaExpiracaoToken).toInstant(ZoneOffset.of("-03:00"));
	}
	
	private Instant genRefreshTokenExpiInstance() {
	    return LocalDateTime.now().plusHours(horaExpiracaoRefreshToken).toInstant(ZoneOffset.of("-03:00"));
	}
	
	public TokenResponseDTO refreshAccessToken(String refreshToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                                      .withIssuer(ISSUER)
                                      .build();
            DecodedJWT jwt = verifier.verify(refreshToken);
            String email = jwt.getSubject();

            Usuario usuario = repository.findByEmail(email)
                    .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));

            return generateTokens(usuario);
        } catch (JWTVerificationException e) {
            throw new AuthorizationException("Refresh token inválido ou expirado");
        }
    }

}
