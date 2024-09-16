package io.gitHub.AugustoMello09.PetHouse.infra.message.producer.producerImpl;

import java.io.Serializable;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Pedido;
import io.gitHub.AugustoMello09.PetHouse.infra.dtos.PagamentoDTO;
import io.gitHub.AugustoMello09.PetHouse.infra.message.producer.PagamentoProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PagamentoProducerImpl implements PagamentoProducer {
	
	private final KafkaTemplate<String, Serializable> kafkaTemplate;

	@Override
	public void sendToTopicCarrinho(Pedido pedido) {
		PagamentoDTO pagamento = new PagamentoDTO();
		pagamento.setIdCarrinho(pedido.getCarrinho().getId());
		pagamento.setIdUsuario(pedido.getUsuario().getId());
		pagamento.setPreco(pedido.getValorTotalPedido());
		pagamento.setCpfOrCnpj(pedido.getUsuario().getCpfCnpj());
		kafkaTemplate.send("Pagamento", pedido.getUsuario().getId().toString(), pagamento);
	}

}
