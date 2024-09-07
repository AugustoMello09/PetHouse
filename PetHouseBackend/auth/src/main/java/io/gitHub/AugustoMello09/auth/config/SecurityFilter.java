package io.gitHub.AugustoMello09.auth.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;

import io.gitHub.AugustoMello09.auth.domain.entities.Usuario;
import io.gitHub.AugustoMello09.auth.infra.UsuarioOpenFeign;
import io.gitHub.AugustoMello09.auth.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UsuarioOpenFeign repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token;
		String authorizationHeader = request.getHeader("Authorization") == null ? request.getParameter("Authorization")
				: request.getHeader("Authorization");

		try {
			if (authorizationHeader != null) {
				token = authorizationHeader.replace("Bearer ", "");
				String email = tokenService.validacaoToken(token);
				Usuario usuario = repository.findByEmail(email).getBody();
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,
						null, usuario.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
			filterChain.doFilter(request, response);
		} catch (TokenExpiredException ex) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("Token expirado. Faça login novamente.");
		}

	}

}
