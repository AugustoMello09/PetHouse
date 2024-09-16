package io.gitHub.AugustoMello09.PetHouse.infra.message.producer;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Pedido;

public interface PagamentoProducer {
	
	void sendToTopicCarrinho(Pedido pedido);

}
