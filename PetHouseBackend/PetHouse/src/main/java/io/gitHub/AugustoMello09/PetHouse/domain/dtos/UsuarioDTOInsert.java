package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTOInsert extends UsuarioDTO {
	private static final long serialVersionUID = 1L;
	
	private String senha;

}
