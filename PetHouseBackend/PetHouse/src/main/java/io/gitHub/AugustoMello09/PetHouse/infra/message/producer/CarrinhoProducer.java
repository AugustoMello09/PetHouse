package io.gitHub.AugustoMello09.PetHouse.infra.message.producer;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;

public interface CarrinhoProducer {
	
	void sendToTopicCarrinho(CarrinhoDTO carrinho);

}
