package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.PlanoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.PlanoVeterinario;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.PlanoVeterinarioRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class PlanoVeterinarioServiceImpl implements PlanoVeterinarioService {
	
	private final PlanoVeterinarioRepository repository;
	
	private final ModelMapper mapper;
	
	public PlanoVeterinarioServiceImpl(PlanoVeterinarioRepository repository, ModelMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public PlanoDTO findById(Long id) {
		Optional<PlanoVeterinario> entity = repository.findById(id);
		PlanoVeterinario plano = entity.orElseThrow(() -> new ObjectNotFoundException("Plano não encontrado"));
		return mapper.map(plano, PlanoDTO.class);
	}

	@Override
	public List<PlanoDTO> listAll() {
		List<PlanoVeterinario> planos = repository.findAll();
		return planos.stream().map(x -> mapper.map(x, PlanoDTO.class))
				.collect(Collectors.toList());
	}

	@Override
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
		repository.deleteById(id);
	}

}
