package io.gitHub.AugustoMello09.PetHouse.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Plano {

	BASICO(0, "Básico"), ESSENCIAL(1, "Essencial"), COMPLETO(2, "Completo");

	private Integer cod;
	private String descricao;

	public static Plano toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Plano x : Plano.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Cod invalido" + cod);
	}

}
