import { PaymentService } from './../../../../service/payment.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Endereco } from './endereco.model';
import { CarrinhoInfo } from 'src/app/model/carrinhoInfo.model';
import { AuthService } from 'src/app/service/auth.service';
import { CarrinhoService } from 'src/app/service/carrinho.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NavigationEnd, Router } from '@angular/router';
import { EnderecoService } from 'src/app/service/endereco.service';
import { jwtDecode } from 'jwt-decode';
import { initAccordions } from 'flowbite';
import { FormGroup, NgForm } from '@angular/forms';
import { Pagamento } from './pagamento.model';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  @ViewChild('f') formA?: NgForm;
  @ViewChild('d') formB?: NgForm;
  @ViewChild('g') formC?: NgForm;
  @ViewChild('a') formD?: NgForm;

  cpfMask: string = '999.999.999-99';

  carrinho: CarrinhoInfo = {
    id: '',
    itemsCarrinho: [
      {
        produtoId: 0,
        quantidade: 0,
        nome: '',
        preco: 0,
        imgProduto: ''
      },
    ],
    valorTotalCarrinho: 0
  };

  endereco: Endereco = {
    logradouro: '',
    numero: '',
    complemento: '',
	  bairro: '',
    cep: '',
    uf: '',
    cidade: ''
  }

  metodoPagamento!: number;
  linkPagamento!: string;

  pagamento: Pagamento = {
    id: '',
    metodoPagamento: '',
    linkPagamento: '',
    preco: 0,
    encodedImage: '',
    payload: '',
    dueDate: '',
    nossoNumero: '',
    identificationField: '',
    description: '',
    barCode: '',
    dataCriacao: '',
    invoiceNumber: '',
    nome: '',
    cpfCnpj: '',
    email: ''
  }

  constructor(private auth: AuthService, private carrinhoService: CarrinhoService, private router: Router,
    private snack: MatSnackBar, private consultaCep: EnderecoService, private paymentService: PaymentService,
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.findById();
    this.carrinhoService.carrinho$.subscribe(carrinho => {
      if (carrinho) {
        this.carrinho = carrinho;
      }
    });
  }

  ngAfterViewInit() {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
       setTimeout(() => {  initAccordions();})
      }
    });
  }

  isAnyFormValid(): boolean {
    const isFormAValid = this.formA?.valid ?? false;
    const isAnyOtherFormValid = (this.formB?.valid ?? false) || (this.formC?.valid ?? false) || (this.formD?.valid ?? false);
    return isFormAValid && isAnyOtherFormValid;
  }

  public findById() {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      this.carrinho.id = decodedToken.carrinhoId;
      this.carrinhoService.findById(this.carrinho.id).subscribe();
    }
  }
  
  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public voltar() {
    this.router.navigate(['']);
  }

  public buscaCep(ev: any, f: NgForm) {
    const cep = ev.target.value;
    if (cep !== '') {
      this.consultaCep.getConsultaCep(cep).subscribe((response) =>
      {
        this.populandoEndereco(response, f);
        console.log(response);
      });
    }
  }

  public populandoEndereco(dados: any, f: NgForm) {
    f.form.patchValue({
      numero: dados.numero,
      complemento: dados.complemento,
      bairro: dados.bairro,
      logradouro: dados.logradouro,
      uf: dados.uf,
      localidade: dados.localidade,

    })
  }

  public onCpfChange(event: any): void {
    const value = event.target.value.replace(/\D/g, ''); 
    this.cpfMask = value.length > 11 ? '99.999.999/9999-99' : '999.999.999-99';  
  }

  public fazerPedido(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      this.carrinho.id = decodedToken.carrinhoId;
      
      if (this.formB?.valid) {
        this.metodoPagamento = 1; 
      } else if (this.formC?.valid) {
        this.metodoPagamento = 0; 
      } else if (this.formD?.valid) {
        this.metodoPagamento = 2; 
      }

      if (this.metodoPagamento !== null) {
        this.spinner.show();
        this.carrinhoService.pedido(this.carrinho.id, this.metodoPagamento)
        .subscribe({
          next: () => {
            setTimeout(() => {
              this.spinner.hide();
              this.obterPagamento(this.carrinho.id); 
            }, 5000);
            this.spinner.hide();
          },
          error: () => {
            this.snack.open('Erro ao processar pagamento', 'OK', { duration: 5000 });
          }
        });
      }  
    }
  }

  public obterPagamento(idCarrinho: any): void {
    this.paymentService.obterPagamento(idCarrinho)
        .subscribe({
            next: (pagamento: Pagamento) => {
                if (pagamento.metodoPagamento === "PIX") {
                    this.redirecionarParaPix(pagamento);
                }
                if (pagamento.metodoPagamento === "BOLETO") {
                    this.redirecionarParaBoleto(pagamento); 
                }
                if (pagamento.metodoPagamento === "CREDIT_CARD") {
                    this.redirecionarCartao(pagamento);  
                }
            },
            error: () => {
                this.snack.open('Erro ao obter link de pagamento', 'OK', { duration: 5000 });
            }
        });
  }

  private redirecionarParaPix(pagamento: Pagamento): void {
    const pixData = {
        encodedImage: pagamento.encodedImage,
        payload: pagamento.payload,
        preco: pagamento.preco,
        dueDate: pagamento.dueDate
    };
    localStorage.setItem('pixData', JSON.stringify(pixData));
    this.router.navigate(['/pix']);
  }

  private redirecionarParaBoleto(pagamento: Pagamento): void {
      const boletoData = {
        preco: pagamento.preco, 
        nossoNumero: pagamento.nossoNumero,
        identificationField: pagamento.identificationField,
        description: pagamento.description,
        barCode: pagamento.barCode,
        dueDate: pagamento.dueDate, 
        dataCriacao: pagamento.dataCriacao,
        invoiceNumber: pagamento.invoiceNumber,
        nome: pagamento.nome,
        cpfCnpj: pagamento.cpfCnpj
    };
    localStorage.setItem('boletoData', JSON.stringify(boletoData)); 
    this.router.navigate(['/boleto']);
  }

  public redirecionarCartao(pagamento: Pagamento): void {
    const cartaoData = {
      id: pagamento.id,
      preco: pagamento.preco, 
      cep: this.endereco.cep,
      linkPagamento: pagamento.linkPagamento,
      email: pagamento.email
    };
    localStorage.setItem('cartaoData', JSON.stringify(cartaoData)); 
    this.router.navigate(['/cartao']);
  }

}
