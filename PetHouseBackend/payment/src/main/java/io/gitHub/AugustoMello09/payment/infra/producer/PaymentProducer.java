package io.gitHub.AugustoMello09.payment.infra.producer;

import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponse;
import io.gitHub.AugustoMello09.payment.infra.entities.Dados;
import io.gitHub.AugustoMello09.payment.infra.entities.UsuarioResponse;

public interface PaymentProducer {
	
	void sentToTopicVendas(Dados dados, UsuarioResponse usuarioResponse, AssasResponse assasResponse);

}
