package io.gitHub.AugustoMello09.PetHouse.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/usuario")
public class UsuarioController {

	private final UsuarioService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> findAllPaged(Pageable page) {
		Page<UsuarioDTO> response = service.findAllPaged(page);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTOInsert usuarioDTO) {
		var newObj = service.createUser(usuarioDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable UUID id) {
		service.updateUser(usuarioDTO, id);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "atribuirCargo/{id}")
	public ResponseEntity<Void> atribuir(@RequestBody UsuarioDTO usuarioDTO, @PathVariable UUID id) {
		service.atribuirCargo(usuarioDTO, id);
		return ResponseEntity.ok().build();
	}

}
