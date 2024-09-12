package io.gitHub.AugustoMello09.auth.infra;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.gitHub.AugustoMello09.auth.domain.entities.Usuario;

@Component
@FeignClient(value = "pethouse", path = "/v1/usuario")
public interface UsuarioOpenFeign {
	
	@GetMapping(value = "buscarEmail")
	public ResponseEntity<Usuario> findByEmail(@RequestParam(value = "email") String email);
	

}
