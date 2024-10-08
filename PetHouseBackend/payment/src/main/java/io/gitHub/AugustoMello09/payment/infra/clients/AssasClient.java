package io.gitHub.AugustoMello09.payment.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import io.gitHub.AugustoMello09.payment.configs.AppConfig;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponse;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponseBoleto;
import io.gitHub.AugustoMello09.payment.infra.entities.AssasResponsePixQrCode;
import io.gitHub.AugustoMello09.payment.infra.entities.Pagamanto;
import io.gitHub.AugustoMello09.payment.infra.entities.UsuarioResponse;

@FeignClient(name = "asaasClient", url = "https://sandbox.asaas.com/api/v3", configuration = AppConfig.class)
public interface AssasClient {

	@GetMapping(value = "/customers")
	UsuarioResponse getCustomerByCpfCnpj(@RequestParam String cpfCnpj);
	
	@PostMapping(value = "/payments")
	AssasResponse postCreatePayment(@RequestBody Pagamanto pagamento);
	
	@GetMapping(value = "/payments/{id}/pixQrCode")
	AssasResponsePixQrCode getPaymentPixQrCod(@PathVariable String id);
	
	@GetMapping(value = "/payments/{id}/identificationField")
	AssasResponseBoleto getPaymentBoleto(@PathVariable String id);

}
	