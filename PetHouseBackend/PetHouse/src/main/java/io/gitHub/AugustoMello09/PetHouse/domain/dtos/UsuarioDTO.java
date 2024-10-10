package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	@NotBlank(message = "Campo obrigatório")
	private String nome;
	
	private String cpfCnpj;
	
	@Email(message = "Entre com um email válido")
	@NotBlank(message = "Campo obrigatório")
	private String email;

	private List<Cargo> cargos = new ArrayList<>();
	
	private UUID idCarrinho;
	
	private UUID idHistorico;
	
	public UsuarioDTO(Usuario entity) {
		id = entity.getId();
		nome = entity.getNome();
		cpfCnpj = entity.getCpfCnpj();
		email = entity.getEmail();
		entity.getCargos().forEach(x -> this.cargos.add(x));
		idCarrinho = entity.getCarrinho().getId();
		idHistorico = entity.getHistorico().getId();
	}
	

}
