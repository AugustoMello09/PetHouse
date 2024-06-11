package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.ProdutoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.services.ProdutoService;


@RestController
@RequestMapping(value = "/v1/produto")
public class ProdutoController {

	private final ProdutoService service;

	public ProdutoController(ProdutoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findAllPaged(Pageable page) {
		Page<ProdutoDTO> pages = service.findAllPaged(page);
		return ResponseEntity.ok().body(pages);
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> create(@RequestBody ProdutoDTO produtoDTO){
		var newObj = service.create(produtoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody ProdutoDTO produtoDTO, @PathVariable Long id) {
		service.updateProduto(produtoDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteProduto(id);
		return ResponseEntity.noContent().build();
	}

}
