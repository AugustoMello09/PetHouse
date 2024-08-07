package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.CategoriaDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Categoria;
import io.gitHub.AugustoMello09.PetHouse.repositories.CategoriaRepository;
import io.gitHub.AugustoMello09.PetHouse.services.CategoriaService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CategoriaServiceImpl implements CategoriaService {

	private final CategoriaRepository repository;

	private final ModelMapper mapper;

	@Override
	public CategoriaDTO findById(Long id) {
		Optional<Categoria> entity = repository.findById(id);
		Categoria categoria = entity.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
		return mapper.map(categoria, CategoriaDTO.class);
	}

	@Override
	public List<CategoriaDTO> listAll() {
		List<Categoria> lista = repository.findAll();
		return lista.stream().map(x -> mapper.map(x, CategoriaDTO.class)).collect(Collectors.toList());
	}

	@Override
	public CategoriaDTO create(CategoriaDTO categoriaDTO) {
		Categoria entity = new Categoria();
		entity.setNomeCategoria(categoriaDTO.getNomeCategoria());
		repository.save(entity);
		return mapper.map(entity, CategoriaDTO.class);
	}

	@Override
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
