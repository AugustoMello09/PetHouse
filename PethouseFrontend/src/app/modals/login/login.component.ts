import { Component, OnInit } from '@angular/core';
import { Login } from './login.model';
import { MatDialog } from '@angular/material/dialog';
import { AuthService } from 'src/app/service/auth.service';
import { Router } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { jwtDecode } from 'jwt-decode';
import { RegistroComponent } from '../registro/registro.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  conta: Login = {
    email: '',
    senha: ''  
  }

  constructor(private dialog: MatDialog, private authService: AuthService, private router: Router,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  public close(): void {
    this.dialog.closeAll();
  }

  public login(): void {
    this.spinner.show();
    this.authService.tentarLogar(this.conta.email, this.conta.senha).subscribe(res => {
      const token_access = JSON.stringify(res);
      localStorage.setItem('token_access', token_access);
      const decodedToken: any = jwtDecode(token_access);
      const role = decodedToken.roles;
      this.dialog.closeAll();
      this.message("Login feito com sucesso!");
        if (role === 'ROLE_ADM') {
          this.router.navigate(['/HomeAdm']);
          this.spinner.hide();
        } else {
          this.router.navigate(['/']);
          this.spinner.hide();
        }
    }, (err:  HttpErrorResponse) => {
      this.addMessageError(err);
      this.spinner.hide();
    })
  }

  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public addMessageError(err: HttpErrorResponse) {
    if (err.status === 403) {
      this.message("Erro de authenticação. ");
    }
    if (err.status === 401) {
      this.message("Senha inválida. ");
    }
    if (err.status === 404) {
      this.message("Email não encontrado. ");
    }
  }

  public criarConta() {
    this.dialog.closeAll();
    this.dialog.open(RegistroComponent)
  }


}
