package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;

	private String nome;

	private String email;
	
	public UsuarioInfo(Usuario entity) {
		id = entity.getId();
		nome = entity.getNome();
		email = entity.getEmail();
	}
}
