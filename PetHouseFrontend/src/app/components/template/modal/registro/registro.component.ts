import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { usuarioRegistro } from 'src/app/model/usuarioRegistro.model';
import { AuthService } from 'src/app/service/auth.service';
import { UsuarioService } from 'src/app/service/usuario.service';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  constructor(private dialog: MatDialog, private authService: AuthService, private service: UsuarioService) { }

  usuario: usuarioRegistro = {
    id: '',
    nome: '',
    email: '',
    senha: '',
    cargos: [
      { id: 2 } 
    ]
  }

  ngOnInit(): void {
  }

  public criar() {
    this.service.create(this.usuario).subscribe(() => {
      this.authService.tentarLogar(this.usuario.email, this.usuario.senha).subscribe(result => {
        const token_access = JSON.stringify(result);
        localStorage.setItem('token_access', token_access);
        this.dialog.closeAll();
      })
    })
  }

  public close() {
    this.dialog.closeAll();
  }

}
