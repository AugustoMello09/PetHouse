package io.gitHub.AugustoMello09.PetHouse.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_historico")
public class Historico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@OneToOne(mappedBy = "historico")
	private Usuario usuario;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();

	public Historico(UUID id, Usuario usuario) {
		super();
		this.id = id;
		this.usuario = usuario;
	}
	
}
