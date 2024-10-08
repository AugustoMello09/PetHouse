package io.gitHub.AugustoMello09.payment.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pagamanto {

	private String customer; 
	private float value;
	private String billingType; 
	private String dueDate;
	private String description;

}
