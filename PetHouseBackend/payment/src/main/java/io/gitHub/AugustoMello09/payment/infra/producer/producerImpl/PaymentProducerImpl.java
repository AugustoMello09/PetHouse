package io.gitHub.AugustoMello09.payment.infra.producer.producerImpl;

import java.io.Serializable;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponse;
import io.gitHub.AugustoMello09.payment.infra.entities.Dados;
import io.gitHub.AugustoMello09.payment.infra.entities.Pagamanto;
import io.gitHub.AugustoMello09.payment.infra.entities.Usuario;
import io.gitHub.AugustoMello09.payment.infra.entities.UsuarioResponse;
import io.gitHub.AugustoMello09.payment.infra.producer.PaymentProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentProducerImpl implements PaymentProducer {

	private final KafkaTemplate<String, Serializable> kafkaTemplate;

	@Override
	public void sentToTopicVendas(Dados dados, UsuarioResponse usuarioResponse, AssasResponse assasResponse) {
		Pagamanto pagamento = new Pagamanto();
		pagamento.setBillingType(dados.getBillingType());
		pagamento.setDueDate(assasResponse.getDueDate());
		pagamento.setValue(dados.getPreco().floatValue());
		for (Usuario usuario : usuarioResponse.getData()) {
			pagamento.setDescription(usuario.getName());
		}
		kafkaTemplate.send("Vendas", dados.getIdUsuario().toString(), pagamento);
	}

}
