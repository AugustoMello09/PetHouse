package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Historico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	private UUID idUsuario;
	
	private List<PedidoDTO> pedidos = new ArrayList<>();
	
	public HistoricoDTO(Historico entity) {
		id = entity.getId();
		idUsuario = entity.getUsuario().getId();
		entity.getPedidos().forEach(pedido -> this.pedidos.add(new PedidoDTO(pedido)));
	}

}
