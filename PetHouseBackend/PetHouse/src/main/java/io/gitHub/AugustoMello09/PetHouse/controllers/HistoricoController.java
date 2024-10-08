package io.gitHub.AugustoMello09.PetHouse.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.PetHouse.services.HistoricoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/historico")
@RequiredArgsConstructor
public class HistoricoController {
	
	private final HistoricoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id){
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}

}