package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_item_pedido")
public class ItemPedido implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    private ItemPedidoId id = new ItemPedidoId();

    @ManyToOne
    @MapsId("pedidoId")
    private Pedido pedido;

    @ManyToOne
    @MapsId("produtoId")
    private Produto produto;

    private int quantidade;
    
    @Column(columnDefinition = "TEXT")
    private String img;

    private BigDecimal preco;
    
    public ItemPedido(Pedido pedido, Produto produto) {
		this.pedido = pedido;
		this.produto = produto;
		this.id = new ItemPedidoId(pedido.getId(), produto.getId());
	}

}
