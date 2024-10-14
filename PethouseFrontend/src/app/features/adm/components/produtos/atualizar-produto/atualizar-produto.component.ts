import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { Categoria } from 'src/app/model/categoria.model';
import { Produto } from 'src/app/model/produto.modal';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-atualizar-produto',
  templateUrl: './atualizar-produto.component.html',
  styleUrls: ['./atualizar-produto.component.css']
})
export class AtualizarProdutoComponent implements OnInit {

  private imagem?: File;

  public produto: Produto = {
    id: 0,
    nome: '',
    descricao: '',
    tipo: '',
    preco: 0,
    img: '',
    categoria: {
      id: 0,
      nomeCategoria: '',
    }
  }

  public categorias: Categoria[] = [];

  constructor(public dialogRef: MatDialogRef<AtualizarProdutoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: any }, private service: ProductService, private snack: MatSnackBar
    , private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.findById(this.data.id);
  }

  public findById(id: number): void {
    this.service.findById(id).subscribe(res => {
      this.produto = res;
    })
  }

  public close(): void {
    this.dialogRef.close();
  }

  public update(): void {
    this.spinner.show();
    this.service.update(this.data.id, this.produto).subscribe(() => {
      this.message("Produto atualizado com sucesso! ");
      const img = this.imagem;
      if (img) {
        this.service.uploadImage(this.data.id, img).subscribe();
      }
      this.dialogRef.close();
      this.spinner.hide();
    }, err => {
      for (const error of err.error.errors) {
        this.addMessageError(error.fieldName, error.message);
        this.spinner.hide();
      }
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
  
  public onFileChange(event: any): void {
    if (event.target.files.length > 0) {
      this.imagem = event.target.files[0];
    }
  }

}
