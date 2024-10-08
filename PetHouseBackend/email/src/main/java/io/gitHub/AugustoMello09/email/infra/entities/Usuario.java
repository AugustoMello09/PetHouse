package io.gitHub.AugustoMello09.email.infra.entities;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	
	private UUID id;
	private String nome;
	private String email;

}
