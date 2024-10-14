import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { CategoriaService } from 'src/app/service/categoria.service';
import { Categoria } from '../categoria.model';
import { AtualizarCategoriaComponent } from '../atualizar-categoria/atualizar-categoria.component';

@Component({
  selector: 'app-gerenciar-categorias',
  templateUrl: './gerenciar-categorias.component.html',
  styleUrls: ['./gerenciar-categorias.component.css']
})
export class GerenciarCategoriasComponent implements OnInit {

  displayedColumns: string[] = ['id', 'nome', 'acoes'];
  dataSource: Categoria[] = [];

  constructor(private dialog: MatDialog, private service: CategoriaService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.listAll();
  }

  public listAll(): void {
    this.service.listAll().subscribe(results => {
      this.dataSource = results;
    })
  }

  public close() {
    this.dialog.closeAll();
  }

  public editCategoria(id: number): void {
    this.dialog.open(AtualizarCategoriaComponent, {
      data: { id: id }
    }).afterClosed().subscribe(() => {
      this.listAll();
    });
  }

  public deleteCategoria(id: number): void {
    this.spinner.show();
    this.service.delete(id).subscribe(() => {
      this.message("Categoria deletada com sucesso!");
      this.spinner.hide();
      this.dialog.closeAll();
    });
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
