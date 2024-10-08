package io.gitHub.AugustoMello09.PetHouse.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status {
	
	PENDENTE(1, "Pendente"), APROVADO(2, "Aprovado");

	private Integer cod;
	private String descricao;

	public static Status toEnum(Integer cod) {
		if (cod == null) {
			return null;
		}

		for (Status x : Status.values()) {
			if (cod.equals(x.cod)) {
				return x;
			}
		}
		throw new IllegalArgumentException("Cod invalido" + cod);
	}

}
