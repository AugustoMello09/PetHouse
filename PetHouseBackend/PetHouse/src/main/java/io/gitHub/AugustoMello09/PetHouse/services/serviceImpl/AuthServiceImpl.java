package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import io.gitHub.AugustoMello09.PetHouse.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.PetHouse.services.AuthService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.AuthorizationException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UsuarioRepository repository;

	@Override
	public Usuario authenticated() {
		try {
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			return repository.findByEmail(username).get();
		} catch (Exception e) {
			throw new AuthorizationException("Usuário inválido");
		}
	}

	@Override
	public void validateSelfOrAdmin(UUID Idusuario) {
		Usuario usuario = authenticated();
		if (Idusuario != null && !usuario.getId().equals(Idusuario) && !usuario.hasCargo("ROLE_ADM")) {
			throw new AuthorizationException("Acesso negado");
		} else if (Idusuario == null && !usuario.hasCargo("ROLE_ADM")) {
			throw new AuthorizationException("Acesso negado");
		}
	}

	@Override
	public boolean isAuthenticated() {
		try {
			authenticated();
			return true;
		} catch (AuthorizationException e) {
			return false;
		}
	}

}
