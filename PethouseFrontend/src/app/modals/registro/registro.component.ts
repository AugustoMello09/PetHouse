import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { AuthService } from 'src/app/service/auth.service';
import { UsuarioService } from 'src/app/service/usuario.service';
import { usuarioRegistro } from './usuarioRegistro.model';
import { CarrinhoInfo } from 'src/app/model/CarrinhoInfo.model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {

  public usuario: usuarioRegistro = {
    id: '',
    nome: '',
    email: '',
    cpfCnpj: '',
    senha: '',
    cargos: [
      { id: 2 } 
    ]
  }

  public carrinho: CarrinhoInfo = {
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

  constructor(private dialog: MatDialog, private authService: AuthService, private service: UsuarioService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  public criar() {
    this.spinner.show();
    this.service.create(this.usuario).subscribe(() => {
      this.message("Conta criada com sucesso. ");
      console.log(this.usuario);
      this.authService.tentarLogar(this.usuario.email, this.usuario.senha).subscribe(result => {
        const token_access = JSON.stringify(result);
        localStorage.setItem('token_access', token_access);
        this.spinner.hide();
        this.dialog.closeAll();
      })
    }, (err: HttpErrorResponse) => {
      if (err.status === 400) {
        if (err.error && err.error.error && err.error.error.includes('email')) {
          this.message('Entre com um email válido.');
        } else {
          for (const error of err.error.errors) {
            this.addMessageError(error.fieldName, error.message);
          }
        }
      }
      this.spinner.hide();
    });
  }

  public addMessageError(fieldName: string, errorMessage: string): void {
    this.message(`${fieldName}: ${errorMessage}`);
  }

  public close(): void {
    this.dialog.closeAll();
  }
 
  public message(msg: string): void {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

}
