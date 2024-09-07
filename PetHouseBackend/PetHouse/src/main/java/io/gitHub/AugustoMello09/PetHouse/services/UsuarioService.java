package io.gitHub.AugustoMello09.PetHouse.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioOpen;

public interface UsuarioService {
	
	UsuarioDTO findById(UUID id);

	Page<UsuarioDTO> findAllPaged(Pageable page);

	UsuarioDTO createUser(UsuarioDTOInsert usuarioDTO);

	void updateUser(UsuarioDTO usuarioDTO, UUID id);

	void deleteUser(UUID id);
	
	void atribuirCargo(UsuarioDTO usuarioDTO, UUID id);
	
	UsuarioOpen findByEmail(String email);

}
