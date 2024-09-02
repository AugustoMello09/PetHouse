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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/carrinho")
public class CarrinhoController {

	private final CarrinhoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}

	@PostMapping(value = "/adicionarAoCarrinho")
	public ResponseEntity<CarrinhoDTO> adicionarProdutos(@RequestBody CarrinhoDTO carrinhoDTO) {
		var carrinho = service.adicionarAoCarrinho(carrinhoDTO);
		return ResponseEntity.ok().body(carrinho);
	}

	@PatchMapping(value = "/removerProduto/IdCarrinho/{IdCarrinho}/IdProduto/{IdProduto}")
	public ResponseEntity<Void> removerProduto(@PathVariable UUID IdCarrinho, @PathVariable Long IdProduto) {
		service.removerProdutoDoCarrinho(IdCarrinho, IdProduto);
		return ResponseEntity.ok().build();
	}

}
