import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Login } from 'src/app/model/login.model';
import { AuthService } from 'src/app/service/auth.service';
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

  constructor(private dialog: MatDialog, private authService: AuthService) { }

  ngOnInit(): void {
  }

  public close() {
    this.dialog.closeAll();
  }

  public login() {
    this.authService.tentarLogar(this.conta.email, this.conta.senha).subscribe(res => {
      const token_access = JSON.stringify(res);
      localStorage.setItem('token_access', token_access);
      this.dialog.closeAll();
    })
  }

  public criarConta() {
    this.dialog.closeAll();
    this.dialog.open(RegistroComponent)
  }

}
