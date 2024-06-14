package io.GitHub.AugustoMello09.PetHouseBackend.controllers;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PedidoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.services.PedidoService;

@RestController
@RequestMapping(value = "/v1/pedido")
public class PedidoController {
	
	private final PedidoService service;

	public PedidoController(PedidoService service) {
		super();
		this.service = service;
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		var response = service.findById(id);
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping(value = "/fazerPedido")
	public ResponseEntity<PedidoDTO> create(@RequestBody PedidoDTO pedidoDTO){
		var newObj = service.create(pedidoDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).body(newObj);
	}

}
