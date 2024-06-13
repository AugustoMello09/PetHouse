package io.GitHub.AugustoMello09.PetHouseBackend.repotories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByCategoriaIdOrderByNome(Long idCategoria);
	
	List<Produto> findByNomeContainingIgnoreCase(String nome);

}
