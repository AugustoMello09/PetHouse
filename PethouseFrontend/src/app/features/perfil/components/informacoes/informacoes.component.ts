import { Component, HostListener, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { LoginComponent } from 'src/app/modals/login/login.component';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-informacoes',
  templateUrl: './informacoes.component.html',
  styleUrls: ['./informacoes.component.css']
})
export class InformacoesComponent implements OnInit {

  public isLoggedIn = false;
  public user: any;
  public showDropdown = false;
  public isAdm = false;

  constructor(private auth: AuthService, private dialog: MatDialog, private router: Router) { }

  ngOnInit(): void {
    this.auth.token$.subscribe(token => {
      this.isLoggedIn = !!token;
      this.user = token ? jwtDecode(token) : null;
    });
    this.admRota();
  }

  @HostListener('document:click', ['$event'])
  public onClick(event: Event): void {
    const target = event.target as HTMLElement;
    const clickedInside = target.closest('.dropdown-container');
    if (!clickedInside) {
      this.showDropdown = false;
    }
  }

  public toggleDropdown(): void {
    this.showDropdown = !this.showDropdown;
  }

  public logout(): void {
    this.auth.logout(); 
    this.router.navigate(['']);
  }

  public open(): void {
    this.dialog.open(LoginComponent);
  }

  public meuCarrinho(): void {
    this.router.navigate(['/meuCarrinho']);
  }

  public admRota(): void {
    const token = this.auth.obterToken();
    if (token) {
      const decodedToken: any = jwtDecode(token);
      const role = decodedToken.roles; 
      this.isAdm = role === 'ROLE_ADM'; 
    }
  }

  public irAdm(): void {
    this.router.navigate(['/HomeAdm']);
  }

}
