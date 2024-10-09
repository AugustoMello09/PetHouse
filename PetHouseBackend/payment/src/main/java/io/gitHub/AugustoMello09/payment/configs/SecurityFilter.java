package io.gitHub.AugustoMello09.payment.configs;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import io.gitHub.AugustoMello09.payment.services.exceptions.AuthorizationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
	
	private static final String ISSUER = "PetHouse";

	@Value("${auth.jwt.token.secret}")
	private String secret;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token;
		String authorizationHeader = request.getHeader("Authorization");

		try {
			if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.replace("Bearer ", "");
				UsernamePasswordAuthenticationToken authentication = validacaoToken(token);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			//e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Token inválido ou expirado");
		}
	}
	
	private UsernamePasswordAuthenticationToken validacaoToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			DecodedJWT jwt = JWT.require(algorithm).withIssuer(ISSUER).build().verify(token);

			String email = jwt.getSubject();
			String roles = jwt.getClaim("roles").asString();

			List<GrantedAuthority> authorities = Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());

			return new UsernamePasswordAuthenticationToken(email, null, authorities);
		} catch (JWTVerificationException e) {
			throw new AuthorizationException("Problema na hora de validar o token");
		}
	}

}
