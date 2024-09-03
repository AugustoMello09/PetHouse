package io.gitHub.AugustoMello09.PetHouse.provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinhoProduto;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.domain.enums.Tipo;

public class CarrinhoProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private static final Tipo TIPO = Tipo.CACHORRO;
	private static final BigDecimal PRECO = new BigDecimal(91.21);
	private static final String DESCRICAO = "Simparic 20mg contém sarolaner e começa a agir 3h após a administração, sendo eficaz por até 35 dias contra infestações após o tratamento. Confira a bula para mais informações sobre a eficácia do medicamento.";
	private static final String NOME = "Antipulgas Simparic 5 a 10kg Cães 20mg 1 comprimido";

	public Carrinho criar() {
		Carrinho carrinho = new Carrinho();
		carrinho.setId(ID);
		carrinho.setItemsCarrinho(new ArrayList<>());
		return carrinho;
	}

	public Carrinho getCarrinhoComItem() {
		Carrinho carrinho = criar();
		ItemCarrinhoProduto itemCarrinho = new ItemCarrinhoProduto();
		itemCarrinho.setProduto(new Produto(1L, NOME, PRECO, DESCRICAO, TIPO, null, null));
		itemCarrinho.setQuantidade(1);
		carrinho.getItemsCarrinho().add(itemCarrinho);
		return carrinho;
	}

}
