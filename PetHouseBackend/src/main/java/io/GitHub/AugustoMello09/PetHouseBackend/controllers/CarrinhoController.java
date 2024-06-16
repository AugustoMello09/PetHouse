package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
