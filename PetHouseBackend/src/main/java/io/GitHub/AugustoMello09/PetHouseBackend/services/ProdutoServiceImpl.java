package io.GitHub.AugustoMello09.PetHouseBackend.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.ProdutoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Categoria;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CategoriaRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.ProdutoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoServiceImpl implements ProdutoService{
	
	private final ProdutoRepository repository;
	private final ModelMapper mapper;
	private final CategoriaRepository categoriaRepository;
	
	public ProdutoServiceImpl(ProdutoRepository repository, ModelMapper mapper, CategoriaRepository categoriaRepository) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.categoriaRepository = categoriaRepository;
	}

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
	public void updateProduto(ProdutoDTO produtoDTO, Long id) {
		Produto entity = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
		entity.setNome(produtoDTO.getNome());
		entity.setDescricao(produtoDTO.getDescricao());
		entity.setPreco(produtoDTO.getPreco());
		entity.setTipo(produtoDTO.getTipo());
		repository.save(entity);
		mapper.map(entity, ProdutoDTO.class);
	}

	@Override
	public void deleteProduto(Long id) {
		findById(id);
		repository.deleteById(id);
	}

	@Override
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

}
