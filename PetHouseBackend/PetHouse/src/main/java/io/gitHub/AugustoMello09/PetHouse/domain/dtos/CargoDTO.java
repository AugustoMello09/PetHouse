package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	@Size(min = 5, max = 25, message = "Obrigatório começar com ROLE_(novo nome do cargo)")
	@NotBlank(message = "Campo obrigatório")
	private String authority;

}
