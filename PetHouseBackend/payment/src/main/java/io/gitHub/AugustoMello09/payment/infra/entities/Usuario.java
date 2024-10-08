package io.gitHub.AugustoMello09.payment.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	private String id;

	private String name;

	private String email;

	private String cpfCnpj;

}
