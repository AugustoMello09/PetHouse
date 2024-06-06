package io.GitHub.AugustoMello09.PetHouseBackend.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.enums.Plano;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_plano")
public class PlanoVeterinario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private BigDecimal preco;
	
	@Column(nullable = false)
	private String descricao;
	
	@Column(nullable = false)
	private Plano plano;
	
	@OneToOne(mappedBy = "plano")
	private Usuario usuario;
	
		

}
