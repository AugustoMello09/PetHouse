package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import io.gitHub.AugustoMello09.PetHouse.domain.enums.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String descricao;
	
	@Column(nullable = false)
	private Tipo tipo;
	
	@Column(columnDefinition = "TEXT")
	private String img;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName = "id")
	private Categoria categoria;

	public Produto(Long id, String nome, BigDecimal preco, String descricao, Tipo tipo, Categoria categoria, String img) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.descricao = descricao;
		this.tipo = tipo;
		this.categoria = categoria;
		this.img = img;
	}
}
