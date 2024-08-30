package io.gitHub.AugustoMello09.PetHouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;

@Repository
public interface CargoRepository  extends JpaRepository<Cargo, Long>{

}
