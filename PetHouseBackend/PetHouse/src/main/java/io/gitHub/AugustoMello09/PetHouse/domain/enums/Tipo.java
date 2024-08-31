package io.gitHub.AugustoMello09.PetHouse.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Tipo {

	CACHORRO(0, "Cachorro"), GATO(1, "Gato");

	private Integer cod;
	private String descricao;

	public static Tipo toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Tipo x : Tipo.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Cod invalido" + cod);
	}

}
