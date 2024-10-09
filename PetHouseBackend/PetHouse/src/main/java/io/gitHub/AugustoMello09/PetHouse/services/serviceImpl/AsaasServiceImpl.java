package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.infra.clients.AssasClient;
import io.gitHub.AugustoMello09.PetHouse.infra.dtos.ClienteAsaasDTO;
import io.gitHub.AugustoMello09.PetHouse.services.AssasService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AsaasServiceImpl implements AssasService {
	
	private final AssasClient client;

	@Override
	@Async
	public void criarClienteAsaas(String name, String cpfCnpj) {
		ClienteAsaasDTO clienteAsaasDTO = new ClienteAsaasDTO(cpfCnpj, name);
		client.criarCliente(clienteAsaasDTO);
	}

}
