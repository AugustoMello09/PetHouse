package io.gitHub.AugustoMello09.email.infra.entities;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dados {
	
	private BigDecimal value;
	private String billingType;
	private String dueDate;
	private String description;

}
