package io.GitHub.AugustoMello09.PetHouseBackend.repotories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long>{

}
