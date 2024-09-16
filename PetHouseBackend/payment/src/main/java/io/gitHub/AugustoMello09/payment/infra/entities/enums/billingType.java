package io.gitHub.AugustoMello09.payment.infra.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum billingType {
	
	BOLETO("BOLETO");
	
	private String descricao;
}
