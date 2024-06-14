package io.GitHub.AugustoMello09.PetHouseBackend.repotories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

}
