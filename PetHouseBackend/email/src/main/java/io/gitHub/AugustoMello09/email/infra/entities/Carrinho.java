package io.gitHub.AugustoMello09.email.infra.entities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carrinho {
	
	private UUID id;
	private UUID idUsuario;
	private BigDecimal valorTotalCarrinho;
	private List<CarrinhoItem> carrinhoItens = new ArrayList<>();

}
