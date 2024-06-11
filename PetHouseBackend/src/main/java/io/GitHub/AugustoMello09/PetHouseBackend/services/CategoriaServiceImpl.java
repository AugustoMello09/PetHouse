package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CategoriaDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Categoria;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CategoriaRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	private final CategoriaRepository repository;
	
	private final ModelMapper mapper;

	public CategoriaServiceImpl(CategoriaRepository repository, ModelMapper mapper) {
		super();
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	@Transactional(readOnly = true)
	public CategoriaDTO findById(Long id) {
		Optional<Categoria> entity = repository.findById(id);
		Categoria categoria = entity.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
		return mapper.map(categoria, CategoriaDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CategoriaDTO> listAll() {
		List<Categoria> lista = repository.findAll();
		return lista.stream().map(x -> mapper.map(x, CategoriaDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public CategoriaDTO create(CategoriaDTO categoriaDTO) {
		Categoria entity = new Categoria();
		entity.setNomeCategoria(categoriaDTO.getNomeCategoria());
		repository.save(entity);
		return mapper.map(entity, CategoriaDTO.class);
	}

	@Override
	@Transactional
	public void updateCategoria(CategoriaDTO categoriaDTO, Long id) {
		Categoria categoria = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
		categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
		mapper.map(categoria, CategoriaDTO.class);
		repository.save(categoria);
	}

	@Override
	public void deleteCategoria(Long id) {
		findById(id);
		repository.deleteById(id);
	}

}
