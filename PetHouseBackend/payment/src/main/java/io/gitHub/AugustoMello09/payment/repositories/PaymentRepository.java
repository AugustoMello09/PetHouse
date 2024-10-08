package io.gitHub.AugustoMello09.payment.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.payment.domain.entities.PagamentoEntity;

@Repository
public interface PaymentRepository extends JpaRepository<PagamentoEntity, UUID> {
	
	 Optional<PagamentoEntity> findByIdCarrinho(UUID idCarrinho);
}
