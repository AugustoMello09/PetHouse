package io.gitHub.AugustoMello09.PetHouse.provider;

import java.math.BigDecimal;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.PlanoVeterinario;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Plano;

public class PlanoProvider {
	
	private static final Plano PLANO = Plano.BASICO;
	private static final BigDecimal PRECO = new BigDecimal(25.00);
	private static final String DESCRICAO = "Plano básico preco bom";
	private static final String NOME = "Básico";
	private static final long ID = 1L;

	public PlanoVeterinario criar() {
		PlanoVeterinario entity = new PlanoVeterinario();
		entity.setId(ID);
		entity.setNome(NOME);
		entity.setDescricao(DESCRICAO);
		entity.setPreco(PRECO);
		entity.setPlano(PLANO);
		return entity;
	}

}
