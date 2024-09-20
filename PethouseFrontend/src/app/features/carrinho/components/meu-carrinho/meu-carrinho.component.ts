import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NavigationEnd, Router } from '@angular/router';
import { initAccordions } from 'flowbite';
import { jwtDecode } from 'jwt-decode';
import { CarrinhoInfo } from 'src/app/model/carrinhoInfo.model';
import { AuthService } from 'src/app/service/auth.service';
import { CarrinhoService } from 'src/app/service/carrinho.service';

@Component({
  selector: 'app-meu-carrinho',
  templateUrl: './meu-carrinho.component.html',
  styleUrls: ['./meu-carrinho.component.css']
})
export class MeuCarrinhoComponent implements OnInit {

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

  constructor(private auth: AuthService, private carrinhoService: CarrinhoService, private router: Router,
    private snack: MatSnackBar
  ) { }

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

  public findById() {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      this.carrinho.id = decodedToken.carrinhoId;
      this.carrinhoService.findById(this.carrinho.id).subscribe();
    }
  }

  public removerItem(produtoId?: number): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      this.carrinho.id = decodedToken.carrinhoId;
      if (produtoId !== undefined) {
        this.carrinhoService.removeItem(this.carrinho.id, produtoId).subscribe(() => {
          this.message("Item removido do carrinho :/ ");
        });
      }
    }  
  }
  
  public voltar() {
    this.router.navigate(['']);
  }

  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public finalizar() {
    this.router.navigate(['pagamento']);
   }


}
