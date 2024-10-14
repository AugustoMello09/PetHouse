import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { Usuario } from 'src/app/model/usuario.model';
import { UsuarioService } from 'src/app/service/usuario.service';

@Component({
  selector: 'app-atualizar-usuario',
  templateUrl: './atualizar-usuario.component.html',
  styleUrls: ['./atualizar-usuario.component.css']
})
export class AtualizarUsuarioComponent implements OnInit {

  public usuario: Usuario = {
    id: '',
    nome: '',
    email: '',
    cpfOrCnpj: '',
    cargos: [
      {
        id: 0,
        authority: ''
      }
    ]
  }

  constructor(public dialogRef: MatDialogRef<AtualizarUsuarioComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: any }, private service: UsuarioService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.getUsuarioById(this.data.id);
  }

  public getUsuarioById(id: number): void {
    this.service.findById(id).subscribe(res => {
        this.usuario = res;
    });
  }

  public atualizar(): void {
    this.spinner.show();
    this.service.update(this.usuario).subscribe(() => {
      this.message("Usuário atualizado com sucesso!");
      this.spinner.hide();
      this.dialogRef.close();
    }, err => {
      for (const error of err.error.errors) {
        this.addMessageError(error.fieldName, error.message);
        this.spinner.hide();
      }
    });
  }

  public close(): void {
    this.dialogRef.close(true);
  }

  public message(msg: string): void {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public addMessageError(fieldName: string, errorMessage: string): void {
    this.message(`${fieldName}: ${errorMessage}`);
  }

}
