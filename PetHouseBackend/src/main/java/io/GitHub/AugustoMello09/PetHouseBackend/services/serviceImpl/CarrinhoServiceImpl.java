package io.GitHub.AugustoMello09.PetHouseBackend.services.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import io.GitHub.AugustoMello09.PetHouseBackend.dtos.CarrinhoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.dtos.ProdutoDTO;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Carrinho;
import io.GitHub.AugustoMello09.PetHouseBackend.entities.Produto;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.CarrinhoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.repotories.ProdutoRepository;
import io.GitHub.AugustoMello09.PetHouseBackend.services.CarrinhoService;
import io.GitHub.AugustoMello09.PetHouseBackend.services.exceptions.ObjectNotFoundException;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	private final CarrinhoRepository repository;
	private final ModelMapper mapper;
	private final ProdutoRepository produtoRepository;


	public CarrinhoServiceImpl(CarrinhoRepository repository, ModelMapper mapper, ProdutoRepository produtoRepository) {
		super();
		this.repository = repository;
		this.mapper = mapper;
		this.produtoRepository = produtoRepository;
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM')")
	public CarrinhoDTO findById(UUID id) {
		Optional<Carrinho> entity = repository.findById(id);
		Carrinho carrinho = entity.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));
		return mapper.map(carrinho, CarrinhoDTO.class);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM','ROLE_OPERATOR')")
	public void adicionarAoCarrinho(CarrinhoDTO carrinhoDTO) {
		Carrinho carrinho = repository.findById(carrinhoDTO.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));

		List<Long> idsProdutos = carrinhoDTO.getProdutos().stream().map(ProdutoDTO::getId).collect(Collectors.toList());

		List<Produto> produtosParaAdicionar = produtoRepository.findAllById(idsProdutos);

		for (Produto produto : produtosParaAdicionar) {
			carrinho.getProdutos().add(produto);
		}
		
		repository.save(carrinho);
	}

	@Override
	@PreAuthorize("hasAnyRole('ROLE_ADM','ROLE_OPERATOR')")
	public void removerProdutoDoCarrinho(UUID idCarrinho, Long idProduto) {
		Carrinho carrinho = repository.findById(idCarrinho)
				.orElseThrow(() -> new ObjectNotFoundException("Carrinho não encontrado"));
		produtoRepository.findById(idProduto).orElseThrow(() -> new ObjectNotFoundException("Produto não encontrado"));
		carrinho.getProdutos().removeIf(x -> x.getId().equals(idProduto));
		repository.save(carrinho);
	}

}
