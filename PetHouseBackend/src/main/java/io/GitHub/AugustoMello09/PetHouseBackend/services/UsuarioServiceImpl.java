package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.UsuarioDTOInsert;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Cargo;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CargoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.DataIntegratyViolationException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ModelMapper mapper;
	private final CargoRepository cargoRepository;

	public UsuarioServiceImpl(UsuarioRepository repository, BCryptPasswordEncoder passwordEncoder, ModelMapper mapper,
			CargoRepository cargoRepository) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
		this.cargoRepository = cargoRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioDTO findById(UUID id) {
		Optional<Usuario> entity = repository.findById(id);
		Usuario usuario = entity.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		return mapper.map(usuario, UsuarioDTO.class);
	}

	@Override
	public Page<UsuarioDTO> findAllPaged(Pageable page) {
		Page<Usuario> entities = repository.findAll(page);
		return entities.map(x -> mapper.map(x, UsuarioDTO.class));
	}

	@Override
	@Transactional
	public UsuarioDTO createUser(UsuarioDTOInsert usuarioDTO) {
		Usuario entity = new Usuario();
		emailAlreadyExists(usuarioDTO);
		entity.setNome(usuarioDTO.getNome());
		entity.setEmail(usuarioDTO.getEmail());
		entity.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
		atribuirCargo(entity, usuarioDTO);
		repository.save(entity);
		return mapper.map(entity, UsuarioDTO.class);
	}

	@Override
	@Transactional
	public void updateUser(UsuarioDTO usuarioDTO, UUID id) {
		Usuario entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		entity.setNome(usuarioDTO.getNome());
		entity.setEmail(usuarioDTO.getEmail());
		mapper.map(entity, UsuarioDTO.class);
		repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteUser(UUID id) {
		findById(id);
		repository.deleteById(id);
	}

	protected void emailAlreadyExists(UsuarioDTO usuarioDTO) {
		Optional<Usuario> entity = repository.findByEmail(usuarioDTO.getEmail());
		if (entity.isPresent() && !entity.get().getId().equals(usuarioDTO.getId())) {
			throw new DataIntegratyViolationException("Email já existe");
		}
	}

	protected void atribuirCargo(Usuario entity, UsuarioDTO usuarioDTO) {
		entity.getCargos().clear();
		for (Cargo cargos : usuarioDTO.getCargos()) {
			Cargo cargo = cargoRepository.findById(cargos.getId())
					.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
			entity.getCargos().add(cargo);
		}
	}

	@Override
	public void atribuirCargo(UsuarioDTO usuarioDTO, UUID id) {
		Usuario usuario = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		atribuirCargo(usuario, usuarioDTO);
		repository.save(usuario);
	}

}
