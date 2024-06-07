package io.GitHub.AugustoMello09.PetHouseBackend.repotories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID>{

	Optional<Usuario> findByEmail(String email);
}
