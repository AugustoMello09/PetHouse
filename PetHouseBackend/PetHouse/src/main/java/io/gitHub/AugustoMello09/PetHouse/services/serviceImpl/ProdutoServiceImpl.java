package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Categoria;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.repositories.CategoriaRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.ProdutoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.ProdutoService;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {
	
	private final ProdutoRepository repository;
	private final ModelMapper mapper;
	private final CategoriaRepository categoriaRepository;

	@Override
	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id) {
		Optional<Produto> entity = repository.findById(id);
		Produto produto = entity.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
		return mapper.map(produto, ProdutoDTO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAllPaged(Pageable page) {
		Page<Produto> lista = repository.findAll(page);
		return lista.map(x -> mapper.map(x, ProdutoDTO.class));
	}

	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public ProdutoDTO create(ProdutoDTO produtoDTO) {
		Produto entity = new Produto();
		entity.setNome(produtoDTO.getNome());
		entity.setDescricao(produtoDTO.getDescricao());
		entity.setPreco(produtoDTO.getPreco());
		entity.setTipo(produtoDTO.getTipo());
		repository.save(entity);
		return mapper.map(entity, ProdutoDTO.class);
	}

	@Override
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public void updateProduto(ProdutoDTO produtoDTO, Long id) {
		Produto entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
		entity.setNome(produtoDTO.getNome());
		entity.setDescricao(produtoDTO.getDescricao());
		entity.setPreco(produtoDTO.getPreco());
		entity.setTipo(produtoDTO.getTipo());
		entity.setImg(null);
		repository.save(entity);
		mapper.map(entity, ProdutoDTO.class);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public void deleteProduto(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public void atribuirCategoria(Long idProduto, Long idCategoria) {
		Produto produto = repository.findById(idProduto)
				.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
		Categoria categoria = categoriaRepository.findById(idCategoria)
				.orElseThrow(() -> new ObjectNotFoundException("Categoria não encontrado"));
		produto.setCategoria(categoria);
		repository.save(produto);
	}
	

	@Override
	public List<ProdutoDTO> findByCategoriaIdOrderByNome(Long idCategoria) {
		Optional<Categoria> categoria = categoriaRepository.findById(idCategoria);
		if (categoria.isEmpty()) {
			throw new ObjectNotFoundException("Categoria não encontrada");
		} 
		return repository.findByCategoriaIdOrderByNome(idCategoria)
				.stream().map(x -> mapper.map(x, ProdutoDTO.class))
				.collect(Collectors.toList());
	}
	
	
	@Override
	public List<ProdutoDTO> findByNomeContaining(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome)
				.stream().map(x -> mapper.map(x, ProdutoDTO.class))
				.collect(Collectors.toList());
	}

}
