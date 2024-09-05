package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.PlanoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.PlanoVeterinario;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Usuario;
import io.gitHub.AugustoMello09.PetHouse.repositories.PlanoRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.UsuarioRepository;
import io.gitHub.AugustoMello09.PetHouse.services.PlanoService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.DataIntegratyViolationException;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PlanoServiceImpl implements PlanoService {

	private final PlanoRepository repository;
	private final ModelMapper mapper;
	private final UsuarioRepository usuarioRepository;
	

	@Override
	@Transactional(readOnly = true)
	public PlanoDTO findById(Long id) {
		Optional<PlanoVeterinario> obj = repository.findById(id);
		PlanoVeterinario entity = obj.orElseThrow(() -> new ObjectNotFoundException("Plano não encontrado"));
		return mapper.map(entity, PlanoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PlanoDTO> listAll(Pageable page) {
		Page<PlanoVeterinario> planos = repository.findAll(page);
		return planos.map(plano -> mapper.map(plano, PlanoDTO.class));
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
	@Transactional
	public void deletePlano(Long id) {
		findById(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegratyViolationException("Não pode deletar Plano que está relacionado com usuário");
		}

	}

	@Override
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
