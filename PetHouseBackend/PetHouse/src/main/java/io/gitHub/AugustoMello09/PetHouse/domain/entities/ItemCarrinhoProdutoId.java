package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ItemCarrinhoProdutoId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID carrinhoId;
	private Long produtoId;

}
