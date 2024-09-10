package io.gitHub.AugustoMello09.PetHouse.infra.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BemVindoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String email;
}
