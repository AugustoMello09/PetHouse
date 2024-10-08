package io.gitHub.AugustoMello09.payment.infra.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillingType {

	BOLETO(0, "boleto"), CREDIT_CARD(1, "credit_card"), PIX(2, "pix");

	private Integer cod;
	private String descricao;

	public static BillingType toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (BillingType x : BillingType.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Cod invalido" + cod);
	}
}
