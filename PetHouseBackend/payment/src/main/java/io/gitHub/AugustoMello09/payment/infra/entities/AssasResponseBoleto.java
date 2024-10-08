package io.gitHub.AugustoMello09.payment.infra.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssasResponseBoleto {
	
	private String identificationField;
	private String nossoNumero;
	private String barCode;


}
