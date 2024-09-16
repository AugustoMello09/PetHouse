package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "tb_usuario")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private String nome;

	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String cpfCnpj;

	@Column(nullable = false)
	private String senha;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "tb_usuario_cargo", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "cargo_id"))
	private Set<Cargo> cargos = new HashSet<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "carrinho_id", referencedColumnName = "id")
	private Carrinho carrinho;
	
	@OneToMany(mappedBy = "usuario")
	private List<Pedido> pedido = new ArrayList<>();
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plano_id", referencedColumnName = "id")
	private PlanoVeterinario plano;

	public Usuario(UUID id, String nome, String email, String senha, Carrinho carrinho, PlanoVeterinario plano, String cpfCnpj) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.carrinho = carrinho;
		this.plano = plano;
		this.cpfCnpj = cpfCnpj;
	}
	
	public boolean hasCargo(String cargoNome) {
		for (Cargo role : cargos) {
			if (role.getAuthority().equals(cargoNome)) {
				return true;
			}
		}
		return false;
	}
	

}
