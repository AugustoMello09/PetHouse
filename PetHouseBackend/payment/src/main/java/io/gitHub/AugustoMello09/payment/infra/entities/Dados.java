package io.gitHub.AugustoMello09.payment.infra.entities;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dados {
	
	private UUID idCarrinho;
	private UUID idUsuario;
	private BigDecimal preco;
	private String cpfOrCnpj;
	private String billingType;
}
