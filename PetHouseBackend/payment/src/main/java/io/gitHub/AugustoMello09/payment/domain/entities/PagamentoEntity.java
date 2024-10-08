package io.gitHub.AugustoMello09.payment.domain.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_payment")
public class PagamentoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "id_carrinho")
    private UUID idCarrinho;

    @Column(name = "id_usuario")
    private UUID idUsuario;
    
    @Column(name = "nome")
    private String nome;
    
    @Column(name = "cpf_cnpj")
    private String cpfCnpj;

    @Column(name = "preco")
    private BigDecimal preco;

    @Column(name = "metodo_pagamento")
    private String metodoPagamento;

    @Column(name = "status_pagamento")
    private String statusPagamento;
    
    @Column(name = "link_pagamento")
    private String linkPagamento;

    @Column(name = "data_criacao")
    private String dataCriacao;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "descricao")
    private String description;
	
    @Column(name = "data_vencimento")
    private String dueDate;
    
    @Column(name = "nosso_numero")
    private String nossoNumero;
    
    @Column(name = "identification_field")
    private String identificationField;
    
    @Column(name = "bar_code")
    private String barCode;
    
    @Column(name = "numero_documento")
    private String invoiceNumber;
    
    @Column(name = "img", columnDefinition = "TEXT")
    private String encodedImage;
	
    @Column(name = "payload", columnDefinition = "TEXT")
    private String payload;

}
