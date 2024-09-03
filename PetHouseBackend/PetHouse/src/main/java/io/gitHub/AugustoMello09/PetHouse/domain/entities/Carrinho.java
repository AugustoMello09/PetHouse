package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter
@Getter
@Entity
@Table(name = "tb_carrinho")
public class Carrinho implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@OneToOne(mappedBy = "carrinho")
	private Usuario usuario;
	
	@OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemCarrinhoProduto> itemsCarrinho = new ArrayList<>();
	
	@OneToMany(mappedBy = "carrinho")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Carrinho(UUID id, Usuario usuario) {
		super();
		this.id = id;
		this.usuario = usuario;
	}
	
	public BigDecimal getValorTotalDosPedidosCarrinho() {
		return pedidos.stream()
				.map(Pedido::getValorTotalPedido)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	
	public BigDecimal getValorTotalCarrinho() {
	    return itemsCarrinho.stream()
	        .map(item -> item.getProduto().getPreco().multiply(new BigDecimal(item.getQuantidade())))
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	}
	

}
