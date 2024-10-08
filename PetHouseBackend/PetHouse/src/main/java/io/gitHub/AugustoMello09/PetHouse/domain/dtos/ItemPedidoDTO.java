package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

	private Long produtoId;
	private String nomeProduto;
	private int quantidade;
	private String img;

	public ItemPedidoDTO(ItemPedido itemPedido) {
		this.produtoId = itemPedido.getProduto().getId();
		this.nomeProduto = itemPedido.getProduto().getNome();
		this.quantidade = itemPedido.getQuantidade();
		this.img = itemPedido.getImg();
	}

}
