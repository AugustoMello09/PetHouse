package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UsuarioOpen implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;

	private String nome;

	private String email;

	private String senha;
	
	private List<Cargo> cargos = new ArrayList<>();
	
	public UsuarioOpen(Usuario entity) {
		id = entity.getId();
		nome = entity.getNome();
		email = entity.getEmail();
		senha = entity.getSenha();
		entity.getCargos().forEach(carg -> this.cargos.add(carg));
	}

}
