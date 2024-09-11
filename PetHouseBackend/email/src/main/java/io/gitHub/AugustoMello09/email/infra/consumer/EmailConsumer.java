package io.gitHub.AugustoMello09.email.infra.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.email.infra.entities.Usuario;
import io.gitHub.AugustoMello09.email.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailConsumer {
	
	private final EmailService service;
	
	@SneakyThrows
	@KafkaListener(topics = "bemVindo", groupId = "stage-one", containerFactory = "jsonContainerFactory")
	public void bemVindo(@Payload Usuario usuario) {
		log.info("Enviando email ...");
		Thread.sleep(1000);
		service.enviarEmailBemVindo(usuario);
		log.info("Email enviado ...");
		Thread.sleep(1000);
	}

}
