import { Component, OnInit } from '@angular/core';
import { Categoria } from '../categoria.model';
import { MatDialog } from '@angular/material/dialog';
import { CategoriaService } from 'src/app/service/categoria.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-criar-categoria',
  templateUrl: './criar-categoria.component.html',
  styleUrls: ['./criar-categoria.component.css']
})
export class CriarCategoriaComponent implements OnInit {

  public categoria: Categoria = {
    id: 0,
    nomeCategoria: ''
  }

  constructor(private dialog: MatDialog, private service: CategoriaService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
  }

  public create(): void {
    this.spinner.show();
    this.service.create(this.categoria)
      .subscribe(res => {
        this.categoria = res;
        this.message("Categoria criada com sucesso! ");
        this.spinner.hide();
        this.dialog.closeAll();
      }, err => {
        for (const error of err.error.errors) {
          this.addMessageError(error.fieldName, error.message); 
          this.spinner.hide();
        }
      })
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

  public addMessageError(fieldName: string, errorMessage: string): void {
    this.message(`${fieldName}: ${errorMessage}`);
  }


}
