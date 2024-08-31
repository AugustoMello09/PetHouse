package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	
	@Override
	public CategoriaDTO findById(Long id) {
		Optional<Categoria> obj = repository.findById(id);
		Categoria entity = obj.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
		return new CategoriaDTO(entity);
	}

	@Override
	public List<CategoriaDTO> listAll() {
		List<Categoria> categorias = repository.findAll();
		return categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
	}

	@Override
	public CategoriaDTO create(CategoriaDTO categoriaDTO) {
		Categoria entity = new Categoria();
		entity.setNomeCategoria(categoriaDTO.getNomeCategoria());
		repository.save(entity);
		return new CategoriaDTO(entity);
	}

	@Override
	public void updateCategoria(CategoriaDTO categoriaDTO, Long id) {
		Categoria categoria = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrada"));
		categoria.setNomeCategoria(categoriaDTO.getNomeCategoria());
		repository.save(categoria);
	}

	@Override
	public void deleteCategoria(Long id) {
		findById(id);
		repository.deleteById(id);
	}

}
