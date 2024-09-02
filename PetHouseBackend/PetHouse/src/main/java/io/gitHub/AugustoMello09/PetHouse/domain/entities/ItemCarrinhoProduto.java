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
@Table(name = "tb_carrinho_produto")
public class ItemCarrinhoProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemCarrinhoProdutoId id = new ItemCarrinhoProdutoId();

	@ManyToOne
	@MapsId("carrinhoId")
	private Carrinho carrinho;

	@ManyToOne
	@MapsId("produtoId")
	private Produto produto;

	private int quantidade;

	private BigDecimal preco;

	private String nome;

	@Column(columnDefinition = "TEXT")
	private String img;

	public ItemCarrinhoProduto(Carrinho carrinho, Produto produto) {
		this.carrinho = carrinho;
		this.produto = produto;
		this.id = new ItemCarrinhoProdutoId(carrinho.getId(), produto.getId());
	}

}
