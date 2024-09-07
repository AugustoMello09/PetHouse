package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CarrinhoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioDTOInsert;
import io.gitHub.AugustoMello09.PetHouse.domain.dtos.UsuarioOpen;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Cargo;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import io.gitHub.AugustoMello09.PetHouse.repositories.CargoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.CarrinhoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.PetHouse.services.UsuarioService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository repository;
	private final CargoRepository cargoRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final CarrinhoRepository carrinhoRepository;
	private final ModelMapper mapper;
	
	@Transactional(readOnly = true)
	@Override
	public UsuarioDTO findById(UUID id) {
		Optional<Usuario> usuario = repository.findById(id);
		Usuario entity = usuario.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		return new UsuarioDTO(entity);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<UsuarioDTO> findAllPaged(Pageable page) {
		Page<Usuario> usuario = repository.findAll(page);
		return usuario.map(UsuarioDTO::new);
	}
	
	@Transactional
	@Override
	public UsuarioDTO createUser(UsuarioDTOInsert usuarioDTO) {
		emailAlreadyExists(usuarioDTO);
		Usuario entity = new Usuario();
		entity.setNome(usuarioDTO.getNome());
		entity.setEmail(usuarioDTO.getEmail());
		entity.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
		atribuirCargo(entity, usuarioDTO);
		criarCarrinho(entity);
		repository.save(entity);
		return new UsuarioDTO(entity);
	}
	
	@Transactional
	@Override
	public void updateUser(UsuarioDTO usuarioDTO, UUID id) {
		Usuario entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		entity.setNome(usuarioDTO.getNome());
		entity.setEmail(usuarioDTO.getEmail());
		repository.save(entity);
		
	}
	
	@Transactional
	@Override
	public void deleteUser(UUID id) {
		findById(id);
		repository.deleteById(id);	
	}

	@Override
	public void atribuirCargo(UsuarioDTO usuarioDTO, UUID id) {
		Usuario usuario = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado"));
		atribuirCargo(usuario, usuarioDTO);
		repository.save(usuario);		
	}
	
	public void atribuirCargo(Usuario entity, UsuarioDTO usuarioDTO) {
		entity.getCargos().clear();
		for (Cargo cargos : usuarioDTO.getCargos()) {
			Cargo cargo = cargoRepository.findById(cargos.getId())
					.orElseThrow(() -> new ObjectNotFoundException("Cargo não encontrado"));
			entity.getCargos().add(cargo);
		}
	}
	
	public void emailAlreadyExists(UsuarioDTO usuarioDTO) {
		Optional<Usuario> entity = repository.findByEmail(usuarioDTO.getEmail());
		if (entity.isPresent() && !entity.get().getId().equals(usuarioDTO.getId())) {
			throw new DataIntegratyViolationException("Email já existe");
		}
	}
	
	public void criarCarrinho(Usuario usuario) {
		Carrinho carrinho = new Carrinho();
		usuario.setCarrinho(carrinho);
		carrinho.setUsuario(usuario);
		carrinhoRepository.save(carrinho);
		mapper.map(carrinho, CarrinhoDTO.class);
	}

	@Override
	public UsuarioOpen findByEmail(String email) {
		Optional<Usuario> usuario = repository.findByEmail(email);
		if(usuario.isPresent()) {
			return new UsuarioOpen(usuario.get());
		}
		throw new ObjectNotFoundException("Email Não encontrado");
	}
	

}
