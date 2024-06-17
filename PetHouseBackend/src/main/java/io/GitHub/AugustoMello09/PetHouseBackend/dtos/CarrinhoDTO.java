package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private UUID idUsuario;
	private List<PedidoDTO> pedidos = new ArrayList<>();
	private BigDecimal valorTotalDosPedidos;
	private List<ProdutoDTO> produtos = new ArrayList<>();
	private BigDecimal valorTotalCarrinho;
	
	public CarrinhoDTO(Carrinho entity) {
		id = entity.getId();
		idUsuario = entity.getUsuario().getId();
		entity.getPedidos().forEach(x -> this.pedidos.add(new PedidoDTO(x)));
		valorTotalDosPedidos = entity.getValorTotalDosPedidosCarrinho();
		entity.getProdutos().forEach(x -> this.produtos.add(new ProdutoDTO(x)));
		valorTotalCarrinho = entity.getValorTotalProdutosCarrinho();
	}

}
