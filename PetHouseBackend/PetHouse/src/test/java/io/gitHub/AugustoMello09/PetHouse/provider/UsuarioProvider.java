package io.gitHub.AugustoMello09.PetHouse.provider;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Historico;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;

public class UsuarioProvider {
	
	private static final UUID ID = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	private static final UUID IDCARRINHO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");
	private static final UUID IDHISTORICO = UUID.fromString("148cf4fc-b379-4e25-8bf4-f73feb06befa");

	private static final String NOME = "José";

	private static final String EMAIL = "meuEmail@gmail.com";

	private static final String SENHA = "123";
	
	private static final String CPFCNPJ = "63.985.026/0001-56";

	private final BCryptPasswordEncoder passwordEncoder;

	public UsuarioProvider(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public Usuario criar() {
		Usuario usuario = new Usuario();
		usuario.setId(ID);
		usuario.setNome(NOME);
		usuario.setEmail(EMAIL);
		usuario.setCpfCnpj(CPFCNPJ);
		usuario.setSenha(passwordEncoder.encode(SENHA));
		Carrinho carrinho = new Carrinho();
		carrinho.setId(IDCARRINHO);
		carrinho.setUsuario(usuario);
		usuario.setCarrinho(carrinho);
		Historico historico = new Historico();
		historico.setId(IDHISTORICO);
		historico.setUsuario(usuario);
		usuario.setHistorico(historico);
		Cargo cargo = new Cargo();
		cargo.setId(1L);
		usuario.getCargos().add(cargo);
		return usuario;
	}


}
