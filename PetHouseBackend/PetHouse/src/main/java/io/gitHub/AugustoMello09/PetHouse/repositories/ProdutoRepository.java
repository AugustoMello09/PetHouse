package io.gitHub.AugustoMello09.PetHouse.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByCategoriaIdOrderByNome(Long idCategoria);

	List<Produto> findByNomeContainingIgnoreCase(String nome);
}
