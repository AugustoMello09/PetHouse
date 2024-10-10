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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioInfo;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioOpen;
import io.gitHub.AugustoMello09.PetHouse.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PetHouse Usuário endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/usuario")
public class UsuarioController {

	private final UsuarioService service;
	
	@Operation(summary = "Busca um usuário por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Trás uma lista paginada de usuários")
	@GetMapping
	public ResponseEntity<Page<UsuarioDTO>> findAllPaged(Pageable page) {
		Page<UsuarioDTO> response = service.findAllPaged(page);
		return ResponseEntity.ok().body(response);
	}

	@Operation(summary = "Cria um usuário")
	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@RequestBody UsuarioDTOInsert usuarioDTO) {
		var newObj = service.createUser(usuarioDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@Operation(summary = "Atualiza um usuário")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody UsuarioDTO usuarioDTO, @PathVariable UUID id) {
		service.updateUser(usuarioDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Deleta um usuário")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Atribui a usuário um cargo")
	@PatchMapping(value = "atribuirCargo/{id}")
	public ResponseEntity<Void> atribuir(@RequestBody UsuarioDTO usuarioDTO, @PathVariable UUID id) {
		service.atribuirCargo(usuarioDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Busca um usuário por Email")
	@GetMapping(value = "buscarEmail")
	public ResponseEntity<UsuarioOpen> findByEmail(@RequestParam(value = "email") String email) {
		var response = service.findByEmail(email);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Busca poucas informações do usuário por ID")
	@GetMapping(value = "/info/{id}")
	public ResponseEntity<UsuarioInfo> findInfoById(@PathVariable UUID id) {
		var response = service.findInfoById(id);
		return ResponseEntity.ok().body(response);
	}

}
