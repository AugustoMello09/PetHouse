package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PlanoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.PlanoVeterinario;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Usuario;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.PlanoVeterinarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.UsuarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.DataIntegratyViolationException;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;
 

@Service
public class PlanoVeterinarioServiceImpl implements PlanoVeterinarioService {

	private final PlanoVeterinarioRepository repository;

	private final ModelMapper mapper;

	private final UsuarioRepository usuarioRepository;

	public PlanoVeterinarioServiceImpl(PlanoVeterinarioRepository repository, ModelMapper mapper,
			UsuarioRepository usuarioRepository) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public PlanoDTO findById(Long id) {
		Optional<PlanoVeterinario> entity = repository.findById(id);
		PlanoVeterinario plano = entity.orElseThrow(() -> new ObjectNotFoundException("Plano não encontrado"));
		return mapper.map(plano, PlanoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PlanoDTO> listAll() {
		List<PlanoVeterinario> planos = repository.findAll();
		return planos.stream().map(x -> mapper.map(x, PlanoDTO.class)).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public PlanoDTO create(PlanoDTO planoDTO) {
		PlanoVeterinario entity = new PlanoVeterinario();
		entity.setNome(planoDTO.getNome());
		entity.setDescricao(planoDTO.getDescricao());
		entity.setPreco(planoDTO.getPreco());
		entity.setPlano(planoDTO.getPlano());
		repository.save(entity);
		return mapper.map(entity, PlanoDTO.class);
	}

	@Override
	@Transactional
	public void updatePlano(PlanoDTO planoDTO, Long id) {
		PlanoVeterinario entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Plano não encontrado"));
		entity.setNome(planoDTO.getNome());
		entity.setDescricao(planoDTO.getDescricao());
		entity.setPreco(planoDTO.getPreco());
		entity.setPlano(planoDTO.getPlano());
		repository.save(entity);
		mapper.map(entity, PlanoDTO.class);
	}

	@Override
	public void deletePlano(Long id) {
		findById(id);
		try {
		  repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegratyViolationException("Não pode deletar Plano que está relacionado com usuário");
		}
	}

	@Override
	@Transactional
	public void getPlan(UUID IdUsuario, Long idPlano) {
		PlanoVeterinario plano = repository.findById(idPlano)
				.orElseThrow(() -> new ObjectNotFoundException("Plano não encontrado"));
		Usuario usuario = usuarioRepository.findById(IdUsuario)
				.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));
		usuario.setPlano(plano);
		plano.setUsuario(usuario);
		usuarioRepository.save(usuario);
		repository.save(plano);
	}

	@Override
	public void removePlan(UUID IdUsuario, Long idPlano) {
		PlanoVeterinario plano = repository.findById(idPlano)
				.orElseThrow(() -> new ObjectNotFoundException("Plano não encontrado"));
		Usuario usuario = usuarioRepository.findById(IdUsuario)
				.orElseThrow(() -> new ObjectNotFoundException("Usuario não encontrado"));

		var usuarioHavPlan = usuario.getPlano() != null && usuario.getPlano().getId().equals(idPlano);

		if (usuarioHavPlan) {
			usuario.setPlano(null);
			usuarioRepository.save(usuario);
		}

		var planoHavUser = plano.getUsuario() != null && plano.getUsuario().getId().equals(IdUsuario);

		if (planoHavUser) {
			plano.setUsuario(null);
			repository.save(plano);
		}
	}

}
