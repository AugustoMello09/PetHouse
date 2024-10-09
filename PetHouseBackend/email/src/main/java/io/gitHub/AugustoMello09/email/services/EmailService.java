package io.gitHub.AugustoMello09.email.services;

import io.gitHub.AugustoMello09.email.infra.entities.CarrinhoItem;
import io.gitHub.AugustoMello09.email.infra.entities.Dados;
import io.gitHub.AugustoMello09.email.infra.entities.Usuario;

public interface EmailService {
	
	void enviarEmailBemVindo(Usuario user);
	
	void enviarEmailAdicionouProdutosCarrinho(CarrinhoItem carrinho, Usuario usuario);
	
	void enviarEmailItensCarrinho(Usuario user);
	
	void enviarEmailVendasBoletoOrPix(Dados dados, Usuario usuario);
	
	void enviarEmailVendasCartao(Dados dados, Usuario usuario);

}
