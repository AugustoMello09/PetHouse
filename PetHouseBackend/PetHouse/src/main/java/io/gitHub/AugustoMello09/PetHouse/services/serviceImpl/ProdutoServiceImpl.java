package io.gitHub.AugustoMello09.PetHouse.services.serviceImpl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import io.gitHub.AugustoMello09.PetHouse.domain.dtos.ProdutoDTO;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Categoria;
import io.gitHub.AugustoMello09.PetHouse.domain.entities.Produto;
import io.gitHub.AugustoMello09.PetHouse.repositories.CategoriaRepository;
import io.gitHub.AugustoMello09.PetHouse.repositories.ProdutoRepository;
import io.gitHub.AugustoMello09.PetHouse.services.ImgService;
import io.gitHub.AugustoMello09.PetHouse.services.ProdutoService;
import io.gitHub.AugustoMello09.PetHouse.services.S3Service;
import io.gitHub.AugustoMello09.PetHouse.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProdutoServiceImpl implements ProdutoService {

	private final ProdutoRepository repository;
	private final ModelMapper mapper;
	private final CategoriaRepository categoriaRepository;
	private final S3Service s3Service;
	private final ImgService imgService;

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
		return repository.findByCategoriaIdOrderByNome(idCategoria).stream().map(x -> mapper.map(x, ProdutoDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ProdutoDTO> findByNomeContaining(String nome) {
		return repository.findByNomeContainingIgnoreCase(nome).stream().map(x -> mapper.map(x, ProdutoDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public void uploadfile(Long idProduto, MultipartFile imagem) throws IOException {
		Produto produto = repository.findById(idProduto)
				.orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado."));

		BufferedImage img = imgService.getJpgImageFromFile(imagem);

		BufferedImage resizedImg = imgService.resize(img, 400);

		BufferedImage croppedImg = imgService.cropSquare(resizedImg);

		InputStream inputStream = imgService.getInputStream(croppedImg, "jpg");

		String fileName = generateFileName(imagem.getOriginalFilename());

		URI uri = s3Service.uploadFile(inputStream, fileName, "image/jpeg");

		produto.setImg(uri.toString());
		repository.save(produto);

	}

	private String generateFileName(String originalFileName) {
		return UUID.randomUUID().toString() + ".jpg";
	}

}
