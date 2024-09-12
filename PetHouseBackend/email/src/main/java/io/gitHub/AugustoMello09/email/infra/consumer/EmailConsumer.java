package io.gitHub.AugustoMello09.email.infra.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.email.infra.clients.UsuarioClient;
import io.gitHub.AugustoMello09.email.infra.entities.CarrinhoItem;
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

	private final UsuarioClient usuarioClient;

	private final Map<UUID, List<CarrinhoItem>> carrinhoAnteriorMap = new HashMap<>();

	@SneakyThrows
	@KafkaListener(topics = "bemVindo", groupId = "stage-one", containerFactory = "jsonContainerFactory")
	public void bemVindo(@Payload Usuario usuario) {
		log.info("Enviando email ...");
		Thread.sleep(1000);
		service.enviarEmailBemVindo(usuario);
		log.info("Email enviado ...");
		Thread.sleep(1000);
	}

	@SneakyThrows
	@KafkaListener(topics = "Carrinho", groupId = "stage-two", containerFactory = "jsonContainerFactory")
	public void carrinho(@Payload List<CarrinhoItem> itemsDto, @Header(KafkaHeaders.RECEIVED_KEY) UUID key) {
		if (!itemsDto.isEmpty()) {

			Usuario usuario = buscarUsuarioPorId(key);

			List<CarrinhoItem> itemsAnteriores = carrinhoAnteriorMap.getOrDefault(key, new ArrayList<>());

			List<CarrinhoItem> novosProdutos = identificarProdutosNovos(itemsDto, itemsAnteriores);

			for (CarrinhoItem item : novosProdutos) {
				service.enviarEmailAdicionouProdutosCarrinho(item, usuario);
			}

			carrinhoAnteriorMap.put(key, new ArrayList<>(itemsDto));
		}
	}

	private Usuario buscarUsuarioPorId(UUID idUsuario) {
		return usuarioClient.findById(idUsuario).getBody();
	}

	private List<CarrinhoItem> identificarProdutosNovos(List<CarrinhoItem> itemsAtuais,
			List<CarrinhoItem> itemsAnteriores) {
		 return itemsAtuais.stream()
		            .filter(itemAtual -> {
		                return itemsAnteriores.stream()
		                    .filter(itemAnterior -> itemAnterior.getIdProduto().equals(itemAtual.getIdProduto()))
		                    .noneMatch(itemAnterior -> itemAnterior.getQuantidade() >= itemAtual.getQuantidade());
		            })
		            .collect(Collectors.toList());
	}

}
