package io.gitHub.AugustoMello09.payment.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.gitHub.AugustoMello09.payment.configs.AppConfig;
import io.gitHub.AugustoMello09.payment.infra.entities.Usuario;

@FeignClient(name = "asaasClient", url = "https://sandbox.asaas.com/api/v3", configuration = AppConfig.class)
public interface AssasClient {
	
	@GetMapping(value = "/customers")
	Usuario getCustomerByCpfCnpj(@RequestParam String cpfCnpj);

}
