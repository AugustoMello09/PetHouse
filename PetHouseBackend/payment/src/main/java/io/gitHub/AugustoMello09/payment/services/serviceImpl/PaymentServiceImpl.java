package io.gitHub.AugustoMello09.payment.services.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.payment.domain.entities.PagamentoEntity;
import io.gitHub.AugustoMello09.payment.infra.clients.AssasClient;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponse;
import io.gitHub.AugustoMello09.payment.infra.entities.Dados;
import io.gitHub.AugustoMello09.payment.infra.entities.Pagamanto;
import io.gitHub.AugustoMello09.payment.infra.entities.Usuario;
import io.gitHub.AugustoMello09.payment.repositories.PaymentRepository;
import io.gitHub.AugustoMello09.payment.services.PaymentService;
import io.gitHub.AugustoMello09.payment.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

	private final AssasClient client;
	private final PaymentRepository repository;

	@Override
	public AssasResponse pagamento(Usuario usuario, Dados dados) {
		Pagamanto pagamento = new Pagamanto();
		pagamento.setCustomer(usuario.getId());
		pagamento.setValue(dados.getPreco().floatValue());
		pagamento.setBillingType(dados.getBillingType());
		pagamento.setDescription("Pedido " + dados.getIdUsuario());

		LocalDate dueDate = LocalDate.now().plusDays(1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		pagamento.setDueDate(dueDate.format(formatter));
		
		AssasResponse link = client.postCreatePayment(pagamento);
		
		return link;
		
	}

	@Override
	public PagamentoEntity findById(UUID idCarrinho) {
		Optional<PagamentoEntity> obj = repository.findByIdCarrinho(idCarrinho);
		PagamentoEntity entity = obj.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));
		return entity;
	}

}
