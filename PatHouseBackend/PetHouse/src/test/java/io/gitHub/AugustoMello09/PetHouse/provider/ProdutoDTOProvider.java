package io.gitHub.AugustoMello09.PetHouse.provider;

import java.math.BigDecimal;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Tipo;

public class ProdutoDTOProvider {
	
	private static final Tipo TIPO = Tipo.CACHORRO;
	private static final BigDecimal PRECO = new BigDecimal(91.21);
	private static final String DESCRICAO = "Simparic 20mg contém sarolaner e começa a agir 3h após a administração, sendo eficaz por até 35 dias contra infestações após o tratamento. Confira a bula para mais informações sobre a eficácia do medicamento.";
	private static final String NOME = "Antipulgas Simparic 5 a 10kg Cães 20mg 1 comprimido";
	private static final long ID = 1L;

	public ProdutoDTO criar() {
		ProdutoDTO produto = new ProdutoDTO();
		produto.setId(ID);
		produto.setNome(NOME);
		produto.setDescricao(DESCRICAO);
		produto.setPreco(PRECO);
		produto.setTipo(TIPO);
		return produto;
	}

}
