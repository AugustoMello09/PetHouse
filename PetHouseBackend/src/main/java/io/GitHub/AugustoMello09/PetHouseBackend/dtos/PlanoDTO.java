package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import java.math.BigDecimal;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.enums.Plano;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlanoDTO {
	
	private Long id;

	private String nome;
	
	private BigDecimal preco;
	
	private String descricao;
	
	private Plano plano;
	
}
