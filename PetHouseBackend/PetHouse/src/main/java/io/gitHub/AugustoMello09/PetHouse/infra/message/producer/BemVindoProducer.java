package io.gitHub.AugustoMello09.PetHouse.infra.message.producer;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;

public interface BemVindoProducer {
	
	void sendToTopic(UsuarioDTO usuarioDTO);

}
