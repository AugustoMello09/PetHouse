package io.gitHub.AugustoMello09.payment.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.gitHub.AugustoMello09.payment.domain.entities.PagamentoEntity;
import io.gitHub.AugustoMello09.payment.services.PaymentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentService service;
	
	@GetMapping(value = "/{idCarrinho}")
	public ResponseEntity<PagamentoEntity> findByCarrinho(@PathVariable UUID idCarrinho) {
		var response = service.findById(idCarrinho);
		return ResponseEntity.ok().body(response);
	}
}
