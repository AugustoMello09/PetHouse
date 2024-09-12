package io.gitHub.AugustoMello09.PetHouse.infra.message.producer.producerImpl;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.infra.dtos.CarrinhoItemDTO;
import io.gitHub.AugustoMello09.PetHouse.infra.message.producer.CarrinhoProducer;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CarrinhoProducerImpl implements CarrinhoProducer {
	
	private final KafkaTemplate<String, Serializable> kafkaTemplate;
	
	@Override
	public void sendToTopicCarrinho(CarrinhoDTO carrinho) {
		 List<CarrinhoItemDTO> itemsDto = carrinho.getItemsCarrinho().stream()
			        .map(itens -> {
			            CarrinhoItemDTO obj = new CarrinhoItemDTO();
			            obj.setIdCarrinho(carrinho.getId());
			            obj.setIdUsuario(carrinho.getIdUsuario());
			            obj.setIdProduto(itens.getProdutoId());
			            obj.setNomeProduto(itens.getNome());
			            obj.setQuantidade(itens.getQuantidade());  
			            obj.setPreco(itens.getPreco());
			            obj.setImgProduto(itens.getImgProduto());
			            return obj;
			        })
			        .collect(Collectors.toList());
		 kafkaTemplate.send("Carrinho", carrinho.getIdUsuario().toString(), (Serializable) itemsDto);
	}

}
