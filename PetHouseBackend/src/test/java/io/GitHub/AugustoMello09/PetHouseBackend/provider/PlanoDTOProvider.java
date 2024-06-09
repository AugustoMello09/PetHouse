package io.GitHub.AugustoMello09.PetHouseBackend.provider;

import java.math.BigDecimal;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PlanoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.enums.Plano;

public class PlanoDTOProvider {
	
	private static final Plano PLANO = Plano.BASICO;
	private static final BigDecimal PRECO = new BigDecimal(25.00);
	private static final String DESCRICAO = "Plano básico preco bom";
	private static final String NOME = "Básico";
	private static final long ID = 1L;

	public PlanoDTO criar() {
		PlanoDTO entity = new PlanoDTO();
		entity.setId(ID);
		entity.setNome(NOME);
		entity.setDescricao(DESCRICAO);
		entity.setPreco(PRECO);
		entity.setPlano(PLANO);
		return entity;
	}

}
