package io.gitHub.AugustoMello09.payment.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.payment.domain.entities.PagamentoEntity;
import io.gitHub.AugustoMello09.payment.services.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Payment Payment endpoint")
@RestController
@RequestMapping(value = "/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService service;
	
	@Operation(summary = "Busca um carrinho por ID")
	@GetMapping(value = "/{idCarrinho}")
	public ResponseEntity<PagamentoEntity> findByCarrinho(@PathVariable UUID idCarrinho) {
		var response = service.findById(idCarrinho);
		return ResponseEntity.ok().body(response);
	}
}
