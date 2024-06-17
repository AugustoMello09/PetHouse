package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.services.CarrinhoService;

@RestController
@RequestMapping(value = "/v1/carrinho")
public class CarrinhoController {
	
	private final CarrinhoService service;

	public CarrinhoController(CarrinhoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/adicionarAoCarrinho")
	public ResponseEntity<Void> adicionarProdutos(@RequestBody CarrinhoDTO carrinhoDTO) {
	    service.adicionarAoCarrinho(carrinhoDTO);
	    return ResponseEntity.ok().build();
	}
	
	@PatchMapping(value = "/removerProduto/IdCarrinho/{IdCarrinho}/IdProduto/{IdProduto}")
	public ResponseEntity<Void> removerProduto(@PathVariable UUID IdCarrinho,@PathVariable Long IdProduto){
		service.removerProdutoDoCarrinho(IdCarrinho, IdProduto);
		return ResponseEntity.ok().build();
	}

}
