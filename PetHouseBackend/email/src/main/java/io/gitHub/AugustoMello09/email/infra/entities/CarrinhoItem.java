package io.gitHub.AugustoMello09.email.infra.entities;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoItem {

	private UUID idCarrinho;
	private UUID idUsuario;
	private Long idProduto;
	private String nomeProduto;
	private int quantidade;
	private BigDecimal preco;
	private String imgProduto;

}
