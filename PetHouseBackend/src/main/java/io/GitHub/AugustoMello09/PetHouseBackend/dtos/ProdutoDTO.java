package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.enums.Tipo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String nome;
	
	private BigDecimal preco;

	private String descricao;
	
	private Tipo tipo;
	
	private CategoriaDTO categoria;
	
	public ProdutoDTO(Produto entity) {
		id = entity.getId();
		nome= entity.getNome();
		preco = entity.getPreco();
		descricao = entity.getDescricao();
		tipo = entity.getTipo();
		categoria = new CategoriaDTO(entity.getCategoria());
	}
	
}
