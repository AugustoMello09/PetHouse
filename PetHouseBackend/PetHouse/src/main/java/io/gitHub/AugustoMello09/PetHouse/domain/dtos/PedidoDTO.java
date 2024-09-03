package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Pedido;
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
	private List<ItemPedidoDTO> itemsPedido = new ArrayList<>();
	private UUID idUsuario;
	private BigDecimal valorTotalPedido;

	
	public PedidoDTO(Pedido entity) {
		id = entity.getId();
		data = entity.getData();
		idUsuario = entity.getUsuario().getId();
		entity.getItemsPedido().forEach(itens -> this.itemsPedido.add(new ItemPedidoDTO(itens)));
		valorTotalPedido = entity.getValorTotalPedido();
	}


}
