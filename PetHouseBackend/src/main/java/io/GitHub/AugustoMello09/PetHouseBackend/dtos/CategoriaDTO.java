package io.GitHub.AugustoMello09.PetHouseBackend.dtos;

import java.io.Serializable;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String nomeCategoria;
	
	public CategoriaDTO(Categoria entity) {
		id = entity.getId();
		nomeCategoria = entity.getNomeCategoria();
	}

}
