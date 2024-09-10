package io.gitHub.AugustoMello09.PetHouse.infra.message.producer.producerImpl;

import java.io.Serializable;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.infra.dtos.BemVindoDTO;
import io.gitHub.AugustoMello09.PetHouse.infra.message.producer.BemVindoProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BemVindoProducerImpl implements BemVindoProducer {

	private final KafkaTemplate<String, Serializable> kafkaTemplate;

	@Override
	public void sendToTopic(UsuarioDTO usuarioDTO) {
		BemVindoDTO obj = new BemVindoDTO(usuarioDTO.getNome(), usuarioDTO.getEmail()); 
		kafkaTemplate.send("bemVindo", obj);
	}

}
