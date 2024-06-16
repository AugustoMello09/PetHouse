package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Pedido;
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
	private List<ProdutoDTO> produtos = new ArrayList<>();
	private UUID idUsuario;
	private BigDecimal valorTotalPedido;
	private UUID idCarrinho;
	
	public PedidoDTO(Pedido entity) {
		id = entity.getId();
		data = entity.getData();
		idUsuario = entity.getUsuario().getId();
		idCarrinho = entity.getCarrinho().getId();
		entity.getProdutos().forEach(x -> this.produtos.add(new ProdutoDTO(x)));
		valorTotalPedido = entity.getValorTotalPedido();
	}

}
