package io.gitHub.AugustoMello09.PetHouse.infra.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.gitHub.AugustoMello09.PetHouse.config.AppConfig;
import io.gitHub.AugustoMello09.PetHouse.infra.dtos.ClienteAsaasDTO;

@FeignClient(name = "asaasClient", url = "https://sandbox.asaas.com/api/v3", configuration = AppConfig.class)
public interface AssasClient {

	@PostMapping("/customers")
	void criarCliente(@RequestBody ClienteAsaasDTO cliente);

}
