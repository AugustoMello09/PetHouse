package io.gitHub.AugustoMello09.payment.services;

import java.util.UUID;

import io.gitHub.AugustoMello09.payment.domain.entities.PagamentoEntity;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponse;
import io.gitHub.AugustoMello09.payment.infra.entities.Dados;
import io.gitHub.AugustoMello09.payment.infra.entities.Usuario;

public interface PaymentService {
	
	AssasResponse pagamento(Usuario usuario, Dados dados);
	
	PagamentoEntity findById(UUID idCarrinho);

}
