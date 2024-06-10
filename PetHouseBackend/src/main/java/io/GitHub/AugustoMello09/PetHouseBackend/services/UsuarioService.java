package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTOInsert;

public interface UsuarioService {

	UsuarioDTO findById(UUID id);

	Page<UsuarioDTO> findAllPaged(Pageable page);

	UsuarioDTO createUser(UsuarioDTOInsert usuarioDTO);

	void updateUser(UsuarioDTO usuarioDTO, UUID id);

	void deleteUser(UUID id);
	
	void atribuirCargo(UsuarioDTO usuarioDTO, UUID id);

}
