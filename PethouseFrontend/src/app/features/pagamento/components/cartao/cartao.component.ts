import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-cartao',
  templateUrl: './cartao.component.html',
  styleUrls: ['./cartao.component.css']
})
export class CartaoComponent implements OnInit {

  id!: '';
  preco!: ''; 
  cep!: '';
  linkPagamento!: '';
  email!: '';
 
  constructor(private router: Router) { }

  ngOnInit(): void {
    const storedcartaoData = localStorage.getItem('cartaoData');
    if (storedcartaoData) {
       const cartaoData = JSON.parse(storedcartaoData);
      this.preco = cartaoData.preco;
      this.id = cartaoData.id;
      this.linkPagamento = cartaoData.linkPagamento;
      this.cep = cartaoData.cep;
      this.email = cartaoData.email;
    }
    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (event.navigationTrigger === 'popstate') {
          localStorage.removeItem('cartaoData');
          this.router.navigate(['/']);
        }
      }
    });
  }

}
