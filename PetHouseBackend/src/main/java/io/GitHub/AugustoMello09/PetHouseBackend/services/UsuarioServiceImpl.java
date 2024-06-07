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
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.DataIntegratyViolationException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository repository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ModelMapper mapper;

	public UsuarioServiceImpl(UsuarioRepository repository, BCryptPasswordEncoder passwordEncoder, ModelMapper mapper) {
		super();
		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
		this.mapper = mapper;
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

	


	

	
	
	
	
	

}
