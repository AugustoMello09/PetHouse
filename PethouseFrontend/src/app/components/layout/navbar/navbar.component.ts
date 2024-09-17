import { Component, Input, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import { jwtDecode } from 'jwt-decode';
import { AuthService } from 'src/app/service/auth.service';
import { CarrinhoService } from 'src/app/service/carrinho.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  @Input() quantidadeItensSacola = 0;

  isDropdownVisible = false;

  constructor(private router: Router, private carrinhoService: CarrinhoService,
    private auth: AuthService
  ) { }

  ngOnInit(): void {
    this.obterQuantidadeItensSacola();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
       setTimeout(() => {  initFlowbite();})
      }
    });
  }

  public obterQuantidadeItensSacola(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const carrinhoId = decodedToken.carrinhoId;
      this.carrinhoService.findById(carrinhoId).subscribe();
    }
  }

  public toggleDropdown() {
    this.isDropdownVisible = !this.isDropdownVisible;
  }
}
