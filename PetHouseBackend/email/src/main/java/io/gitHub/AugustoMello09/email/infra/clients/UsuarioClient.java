package io.gitHub.AugustoMello09.email.infra.clients;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import io.gitHub.AugustoMello09.email.infra.entities.Usuario;

@FeignClient(value = "pethouse", path = "/v1/usuario")
public interface UsuarioClient {
	
	@GetMapping(value = "/info/{id}")
	public ResponseEntity<Usuario> findById(@PathVariable UUID id);

}
