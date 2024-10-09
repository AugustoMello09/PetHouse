package io.gitHub.AugustoMello09.payment.infra.consumer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import io.gitHub.AugustoMello09.payment.domain.entities.PagamentoEntity;
import io.gitHub.AugustoMello09.payment.infra.clients.AssasClient;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponse;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponseBoleto;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponsePixQrCode;
import io.gitHub.AugustoMello09.payment.infra.entities.Dados;
import io.gitHub.AugustoMello09.payment.infra.entities.Usuario;
import io.gitHub.AugustoMello09.payment.infra.entities.UsuarioResponse;
import io.gitHub.AugustoMello09.payment.infra.producer.PaymentProducer;
import io.gitHub.AugustoMello09.payment.repositories.PaymentRepository;
import io.gitHub.AugustoMello09.payment.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class PaymentConsumer {

	private final AssasClient assasClient;
	private final PaymentService service;
	private final PaymentRepository repository;
	private final PaymentProducer producer;

	@SneakyThrows
	@KafkaListener(topics = "Pagamento", groupId = "stage-three", containerFactory = "jsonContainerFactory")
	public void pagamento(@Payload Dados pagamento, @Header(KafkaHeaders.RECEIVED_KEY) UUID key) {

		Thread.sleep(1000);

		log.info("Buscando usuário na Assas ...");

		UsuarioResponse usuarioResponse = assasClient.getCustomerByCpfCnpj(pagamento.getCpfOrCnpj());

		Thread.sleep(1000);

		if (usuarioResponse != null && usuarioResponse.getData() != null && !usuarioResponse.getData().isEmpty()) {

			for (Usuario usuario : usuarioResponse.getData()) {

				AssasResponse novoLink = service.pagamento(usuario, pagamento);

				Optional<PagamentoEntity> pagamentoExistente = repository.findByIdCarrinho(pagamento.getIdCarrinho());

				PagamentoEntity pagamentoEntity = pagamentoExistente.orElse(new PagamentoEntity());

				pagamentoEntity.setIdCarrinho(pagamento.getIdCarrinho());
				pagamentoEntity.setIdUsuario(pagamento.getIdUsuario());
				pagamentoEntity.setPreco(pagamento.getPreco()); 
				pagamentoEntity.setMetodoPagamento(pagamento.getBillingType());
				pagamentoEntity.setDataCriacao(novoLink.getDateCreated());
				pagamentoEntity.setCpfCnpj(usuario.getCpfCnpj());
				pagamentoEntity.setNome(usuario.getName());
				pagamentoEntity.setEmail(usuario.getEmail());

				if ("BOLETO".equals(pagamento.getBillingType())) {
					AssasResponseBoleto boleto = assasClient.getPaymentBoleto(novoLink.getId());
					pagamentoEntity.setBarCode(boleto.getBarCode());
					pagamentoEntity.setNossoNumero(boleto.getNossoNumero());
					pagamentoEntity.setIdentificationField(boleto.getIdentificationField());
					pagamentoEntity.setDueDate(novoLink.getDueDate());
					pagamentoEntity.setDescription(novoLink.getDescription());
					pagamentoEntity.setInvoiceNumber(novoLink.getInvoiceNumber());
					pagamentoEntity.setStatusPagamento("PENDENTE");
					
				} else if ("PIX".equals(pagamento.getBillingType())) {
					AssasResponsePixQrCode pix = assasClient.getPaymentPixQrCod(novoLink.getId());
					pagamentoEntity.setEncodedImage(pix.getEncodedImage());
					pagamentoEntity.setPayload(pix.getPayload());
					pagamentoEntity.setStatusPagamento("PENDENTE");
				} else {
					pagamentoEntity.setLinkPagamento("Cartão");
					pagamentoEntity.setStatusPagamento("APROVADO");
				}
				
				producer.sentToTopicVendas(pagamento, usuarioResponse, novoLink);
				repository.save(pagamentoEntity);

			}
		}

	}

}
