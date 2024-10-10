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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CategoriaDTO;
import io.gitHub.AugustoMello09.PetHouse.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "PetHouse Categoria endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/categoria")
public class CategoriaController {
	
	private final CategoriaService service;
	
	@Operation(summary = "Busca uma categoria por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id){
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}	
	
	@Operation(summary = "Busca todas as categorias")
	@GetMapping
	public ResponseEntity<List<CategoriaDTO>> listAll() {
		List<CategoriaDTO> lista = service.listAll();
		return ResponseEntity.ok().body(lista);
	}
	
	@Operation(summary = "Cria uma categoria")
	@PostMapping
	public ResponseEntity<CategoriaDTO> create(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		var newObj = service.create(categoriaDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@Operation(summary = "Atualiza uma categoria")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Long id) {
		service.updateCategoria(categoriaDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Deleta uma categoria")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteCategoria(id);
		return ResponseEntity.noContent().build();
	}
}
