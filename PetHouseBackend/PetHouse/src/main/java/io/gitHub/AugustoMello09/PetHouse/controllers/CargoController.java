package io.gitHub.AugustoMello09.PetHouse.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CargoDTO;
import io.gitHub.AugustoMello09.PetHouse.services.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "PetHouse Cargo endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/cargo")
public class CargoController {

	private final CargoService service;
	
	@Operation(summary = "Busca um cargo por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Lista normal todos os cargos")
	@GetMapping
	public ResponseEntity<List<CargoDTO>> findAll(){
		List<CargoDTO> response = service.listAll();
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Cria um cargo")
	@PostMapping
	public ResponseEntity<CargoDTO> create(@Valid @RequestBody CargoDTO cargoDTO) {
		var newObj = service.create(cargoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(newObj.getId())
				.toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@Operation(summary = "Atualiza um cargo")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CargoDTO cargoDTO,@PathVariable Long id) {
	    service.updateCargo(cargoDTO, id);
	    return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Deleta um cargo")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.deleteCargo(id);
		return ResponseEntity.noContent().build();
	}
}
