package io.gitHub.AugustoMello09.PetHouse.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, UUID>{

}
