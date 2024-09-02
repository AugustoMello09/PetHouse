package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.ItemCarrinhoProduto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemCarrinhoProdutoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long produtoId;
    private int quantidade;
    private BigDecimal preco;
    private String nome;
    private String imgProduto;
    
    public ItemCarrinhoProdutoDTO(ItemCarrinhoProduto entity) {
        this.produtoId = entity.getProduto().getId();
        this.quantidade = entity.getQuantidade();
        this.preco = entity.getProduto().getPreco();
        this.nome = entity.getProduto().getNome();
        this.imgProduto = entity.getProduto().getImg();
    }


}
