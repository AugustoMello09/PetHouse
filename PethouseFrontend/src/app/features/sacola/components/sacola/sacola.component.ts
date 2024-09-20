import { Component, HostListener, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { CarrinhoInfo } from 'src/app/model/carrinhoInfo.model';
import { AuthService } from 'src/app/service/auth.service';
import { CarrinhoService } from 'src/app/service/carrinho.service';

@Component({
  selector: 'app-sacola',
  templateUrl: './sacola.component.html',
  styleUrls: ['./sacola.component.css']
})
export class SacolaComponent implements OnInit {

  logado: boolean = false;

  showDropdown = false;

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

  @Input() quantidade: number = 0;

  constructor(private auth: AuthService, private carrinhoService: CarrinhoService, private router: Router) { }

  ngOnInit(): void {
    this.auth.authState$.subscribe(logado => {
      this.logado = logado;
      if (this.logado) {  
        this.findById();
      }
    });

    this.carrinhoService.carrinho$.subscribe(carrinho => {
      if (carrinho) {
        this.carrinho = carrinho;
        this.quantidade = this.carrinho.itemsCarrinho.reduce((total, item) => total + item.quantidade, 0);
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
  
    @HostListener('document:click', ['$event'])
    onClick(event: Event): void {
      const target = event.target as HTMLElement;
      const clickedInside = target.closest('.dropdown-container');
      if (!clickedInside) {
        this.showDropdown = false;
      }
    }

    toggleDropdown(): void {
      this.showDropdown = !this.showDropdown;
    }
  
  public irCarrinho(): void {
    this.router.navigate(['/meuCarrinho']);
  }

}
