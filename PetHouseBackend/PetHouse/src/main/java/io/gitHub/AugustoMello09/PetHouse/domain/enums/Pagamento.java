package io.gitHub.AugustoMello09.PetHouse.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Pagamento {
	
	BOLETO(0, "boleto"), CREDIT_CARD(1, "credit_card"), PIX(2, "pix");
	
	private Integer cod;
	private String descricao;

	public static Pagamento toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Pagamento x : Pagamento.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Cod invalido" + cod);
	}

}
