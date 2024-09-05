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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PlanoDTO;
import io.gitHub.AugustoMello09.PetHouse.services.PlanoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/plano")
public class PlanoVeterinarioController {
	
	private final PlanoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping
	public ResponseEntity<Page<PlanoDTO>> listAll(Pageable page){
		Page<PlanoDTO> lista = service.listAll(page);
		return ResponseEntity.ok().body(lista);
	}
	
	@PostMapping
	public ResponseEntity<PlanoDTO> crate(@RequestBody PlanoDTO planoDTO){
		var newObj = service.create(planoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody PlanoDTO planoDTO,@PathVariable Long id) {
		service.updatePlano(planoDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deletePlano(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/contrarPlano/{idPlano}/idUsuario/{idUsuario}")
	public ResponseEntity<Void> getPlan(@PathVariable Long idPlano, @PathVariable UUID idUsuario) {
		service.getPlan(idUsuario, idPlano);
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping(value = "/removerPlano/{idPlano}/idUsuario/{idUsuario}")
	public ResponseEntity<Void> removePlan(@PathVariable Long idPlano, @PathVariable UUID idUsuario) {
		service.removePlan(idUsuario, idPlano);
		return ResponseEntity.ok().build();
	}

}
