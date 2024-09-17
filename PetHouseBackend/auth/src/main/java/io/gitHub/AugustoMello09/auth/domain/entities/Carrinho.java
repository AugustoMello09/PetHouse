package io.gitHub.AugustoMello09.auth.domain.entities;

import java.io.Serializable;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Carrinho implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;

}
