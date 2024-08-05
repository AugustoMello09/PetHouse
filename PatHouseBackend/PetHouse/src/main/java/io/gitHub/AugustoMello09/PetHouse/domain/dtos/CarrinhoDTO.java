package io.gitHub.AugustoMello09.PetHouse.domain.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.gitHub.AugustoMello09.PetHouse.domain.entities.Carrinho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UUID id;
    private UUID idUsuario;
    private BigDecimal valorTotalDosPedidos;
    private BigDecimal valorTotalCarrinho;
    private List<ItemCarrinhoDTO> itemsCarrinho = new ArrayList<>();
    
    public CarrinhoDTO(Carrinho entity) {
        id = entity.getId();
        idUsuario = entity.getUsuario().getId();
        valorTotalDosPedidos = entity.getValorTotalDosPedidosCarrinho();
        valorTotalCarrinho = entity.getValorTotalCarrinho();
        entity.getItemsCarrinho().forEach(item -> this.itemsCarrinho.add(new ItemCarrinhoDTO(item)));
    }
}
