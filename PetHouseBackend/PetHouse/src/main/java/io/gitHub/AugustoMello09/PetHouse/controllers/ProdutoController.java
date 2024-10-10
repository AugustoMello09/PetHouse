package io.gitHub.AugustoMello09.PetHouse.controllers;

import java.net.URI;
import java.util.List;

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

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;
import io.gitHub.AugustoMello09.PetHouse.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "PetHouse Produto endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping(value =  "/v1/produto")
public class ProdutoController {
	
	private final ProdutoService service;
	
	@Operation(summary = "Busca um produto por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Long id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Trás uma lista paginada de produtos")
	@GetMapping
	public ResponseEntity<Page<ProdutoDTO>> findAllPaged(Pageable page) {
		Page<ProdutoDTO> pages = service.findAllPaged(page);
		return ResponseEntity.ok().body(pages);
	}
	
	@Operation(summary = "Cria um produto")
	@PostMapping
	public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoDTO produtoDTO){
		var newObj = service.create(produtoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}
	
	@Operation(summary = "Atualiza um produto")
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ProdutoDTO produtoDTO, @PathVariable Long id) {
		service.updateProduto(produtoDTO, id);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Deleta um produto")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.deleteProduto(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Atribui uma categoria a um produto")
	@PostMapping(value = "/atribuirCategoria/idCategoria/{idCategoria}/idProduto/{idProduto}")
	public ResponseEntity<Void> atribuirCategoria(@PathVariable Long idProduto, @PathVariable Long idCategoria) {
		service.atribuirCategoria(idProduto, idCategoria);
		return ResponseEntity.ok().build();
	}
	
	@Operation(summary = "Lista todos os produtos por categoria")
	@GetMapping(value = "/listaDeProtudosPorCategoria/idCategoria/{idCategoria}")
	public ResponseEntity<List<ProdutoDTO>> findByCategoriaIdOrderByNome(@PathVariable Long idCategoria) {
		List<ProdutoDTO> lista = service.findByCategoriaIdOrderByNome(idCategoria);
		return ResponseEntity.ok().body(lista);
	}
	
	@Operation(summary = "Busca por produto com o nome")
	@GetMapping(value = "/buscaPorNome/nomeProduto/{nome}")
	public ResponseEntity<List<ProdutoDTO>> findByNomeContaining(@PathVariable String nome) {
		List<ProdutoDTO> lista = service.findByNomeContaining(nome);
		return ResponseEntity.ok().body(lista);
	}

}
