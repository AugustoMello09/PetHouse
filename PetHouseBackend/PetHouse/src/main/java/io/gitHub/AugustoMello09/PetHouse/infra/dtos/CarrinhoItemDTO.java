package io.gitHub.AugustoMello09.PetHouse.infra.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarrinhoItemDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID idCarrinho;
	private UUID idUsuario;
	private Long idProduto;
	private String nomeProduto;
	private int quantidade;
	private BigDecimal preco;
	private String imgProduto;

}
