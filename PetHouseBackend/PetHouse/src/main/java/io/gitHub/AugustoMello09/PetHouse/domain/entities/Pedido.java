package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "tb_pedido")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private LocalDate data;

	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itemsPedido = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "carrinho_id", referencedColumnName = "id")
	private Carrinho carrinho;

	public Pedido(UUID id, LocalDate data, Usuario usuario, Carrinho carrinho) {
		super();
		this.id = id;
		this.data = data;
		this.usuario = usuario;
		this.carrinho = carrinho;
	}

	public BigDecimal getValorTotalPedido() {
		return itemsPedido.stream().map(item -> item.getPreco().multiply(new BigDecimal(item.getQuantidade())))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
