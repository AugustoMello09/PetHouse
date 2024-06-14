package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Pedido;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private LocalDate data;
	private List<Produto> produtos = new ArrayList<>();
	private UUID idUsuario;
	private BigDecimal valorTotalPedido;
	
	public PedidoDTO(Pedido entity) {
		id = entity.getId();
		data = entity.getData();
		entity.getProdutos().forEach(x -> this.produtos.add(x));
		idUsuario = entity.getUsuario().getId();
		valorTotalPedido = entity.getValorTotalPedido();
	}

}
