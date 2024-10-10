package io.gitHub.AugustoMello09.PetHouse.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PedidoDTO;
import io.gitHub.AugustoMello09.PetHouse.services.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "PetHouse Pedido endpoint")
@RestController
@RequestMapping(value = "/v1/pedido")
@RequiredArgsConstructor
public class PedidoController {
	
	private final PedidoService service;
	
	@Operation(summary = "Busca um pedido por ID")
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@Operation(summary = "Cria um pedido")
	@PostMapping(value = "/fazerPedido/{idCarrinho}/formaPagamento/{formaPagamento}")
	public ResponseEntity<PedidoDTO> create(@PathVariable UUID idCarrinho,@PathVariable Integer formaPagamento){
		var newObj = service.create(idCarrinho, formaPagamento);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

}
