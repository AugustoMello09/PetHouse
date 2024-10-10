package io.gitHub.AugustoMello09.PetHouse.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.services.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PetHouse Carrinho endpoint")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/carrinho")
public class CarrinhoController {

	private final CarrinhoService service;
	
	@Operation(summary = "Busca um carrinho por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Adiciona um produto ao carrinho")
	@PostMapping(value = "/adicionarAoCarrinho")
	public ResponseEntity<CarrinhoDTO> adicionarProdutos(@RequestBody CarrinhoDTO carrinhoDTO) {
		var carrinho = service.adicionarAoCarrinho(carrinhoDTO);
		return ResponseEntity.ok().body(carrinho);
	}
	
	@Operation(summary = "Remove um produto do carrinho")
	@PatchMapping(value = "/removerProduto/IdCarrinho/{IdCarrinho}/IdProduto/{IdProduto}")
	public ResponseEntity<Void> removerProduto(@PathVariable UUID IdCarrinho, @PathVariable Long IdProduto) {
		service.removerProdutoDoCarrinho(IdCarrinho, IdProduto);
		return ResponseEntity.ok().build();
	}

}
