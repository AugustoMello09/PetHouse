package io.gitHub.AugustoMello09.payment.infra.consumer;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.payment.infra.clients.AssasClient;
import io.gitHub.AugustoMello09.payment.infra.entities.Dados;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class PaymentConsumer {
	
	private final AssasClient assasClient;
	
	@SneakyThrows
	@KafkaListener(topics = "Pagamento", groupId = "stage-three", containerFactory = "jsonContainerFactory")
	public void pagamento(@Payload Dados pagamento, @Header(KafkaHeaders.RECEIVED_KEY) UUID key) {
		Thread.sleep(1000);
		log.info("Buscando usuário na Assas ...");
		assasClient.getCustomerByCpfCnpj(pagamento.getCpfOrCnpj());
		
		
	}

}
