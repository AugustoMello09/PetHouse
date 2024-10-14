import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { CategoriaService } from 'src/app/service/categoria.service';
import { Categoria } from '../categoria.model';

@Component({
  selector: 'app-atualizar-categoria',
  templateUrl: './atualizar-categoria.component.html',
  styleUrls: ['./atualizar-categoria.component.css']
})
export class AtualizarCategoriaComponent implements OnInit {

  public categoria: Categoria = {
    id: 0,
    nomeCategoria: ''
  }

  constructor(public dialogRef: MatDialogRef<AtualizarCategoriaComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number }, private service: CategoriaService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.getCategoriaById(this.data.id);
  }

  public getCategoriaById(id: number): void {
    this.service.findById(id).subscribe(categoria => {
      this.categoria = categoria;
    });
  }

  public atualizar(): void {
    this.spinner.show();
    this.service.update(this.categoria).subscribe(() => {
      this.message("Atualização feita com sucesso. ");
      this.spinner.hide();
      this.dialogRef.close(true);
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
