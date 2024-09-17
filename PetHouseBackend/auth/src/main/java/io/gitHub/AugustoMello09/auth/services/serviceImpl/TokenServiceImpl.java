package io.gitHub.AugustoMello09.auth.services.serviceImpl;

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
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import io.gitHub.AugustoMello09.auth.domain.dtos.AuthenticationDTO;
import io.gitHub.AugustoMello09.auth.domain.dtos.TokenResponseDTO;
import io.gitHub.AugustoMello09.auth.domain.entities.Cargo;
import io.gitHub.AugustoMello09.auth.domain.entities.Usuario;
import io.gitHub.AugustoMello09.auth.infra.UsuarioOpenFeign;
import io.gitHub.AugustoMello09.auth.services.TokenService;
import io.gitHub.AugustoMello09.auth.services.exceptions.ObjectNotFoundException;

@Service
public class TokenServiceImpl implements UserDetailsService, TokenService {

	@Value("${auth.jwt.token.secret}")
	private String secret;

	@Value("${auth.jwt.token.expiration}")
	private Integer horaExpiracaoToken;

	@Value("${auth.jwt.refresh-token.expiration}")
	private Integer horaExpiracaoRefreshToken;

	private static final String ISSUER = "PetHouse";

	@Autowired
	private UsuarioOpenFeign repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = repository.findByEmail(email).getBody();
		if (user == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		return user;
	}

	public TokenResponseDTO obterToken(AuthenticationDTO authDto) {
		Usuario user = repository.findByEmail(authDto.email()).getBody();
		if (user == null) {
			throw new UsernameNotFoundException("Obter Token falhou");
		}
		return generateTokens(user);
	}

	@Override
	public TokenResponseDTO generateTokens(Usuario usuario) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String rolesAsString = String.join(",",
					usuario.getCargos().stream().map(Cargo::getAuthority).collect(Collectors.toList()));

			String accessToken = JWT.create().withIssuer(ISSUER).withSubject(usuario.getEmail())
					.withClaim("id", usuario.getId().toString()).withClaim("nome", usuario.getNome())
					.withClaim("email", usuario.getEmail()).withClaim("roles", rolesAsString)
					.withClaim("carrinhoId", usuario.getIdCarrinho().toString())
					.withExpiresAt(genExpiInstance()).sign(algorithm);

			String refreshToken = JWT.create().withIssuer(ISSUER).withSubject(usuario.getEmail())
					.withExpiresAt(genRefreshTokenExpiInstance()).sign(algorithm);

			return new TokenResponseDTO(accessToken, refreshToken);
		} catch (JWTCreationException e) {
			throw new RuntimeException("Erro na hora de gerar os tokens");
		}
	}

	@Override
	public String validacaoToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer(ISSUER).build().verify(token).getSubject();
		} catch (JWTCreationException e) {
			throw new IllegalArgumentException("Problema na hora de validar o token");
		}
	}

	private Instant genExpiInstance() {
		return LocalDateTime.now().plusHours(horaExpiracaoToken).toInstant(ZoneOffset.of("-03:00"));
	}

	private Instant genRefreshTokenExpiInstance() {
		return LocalDateTime.now().plusHours(horaExpiracaoRefreshToken).toInstant(ZoneOffset.of("-03:00"));
	}

	@Override
	public TokenResponseDTO refreshAccessToken(String refreshToken) {
		try {

			Algorithm algorithm = Algorithm.HMAC256(secret);
			JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).acceptExpiresAt(3600).build();
			DecodedJWT jwt = verifier.verify(refreshToken);

			String email = jwt.getSubject();

			Usuario usuario = repository.findByEmail(email).getBody();
			if (usuario == null) {
				throw new ObjectNotFoundException("Email não encontrado");
			}

			return generateTokens(usuario);
		} catch (JWTVerificationException e) {
			if (e instanceof TokenExpiredException) {
				Algorithm.HMAC256(secret);
				DecodedJWT jwt = JWT.decode(refreshToken);
				String email = jwt.getSubject();

				Usuario usuario = repository.findByEmail(email).getBody();
				if (usuario == null) {
					throw new ObjectNotFoundException("Email não encontrado");
				}

				return generateTokens(usuario);
			} else {
				throw new IllegalArgumentException("Refresh token inválido");
			}
		}
	}

}
