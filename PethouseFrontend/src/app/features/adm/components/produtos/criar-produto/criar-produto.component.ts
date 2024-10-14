import { Component, OnInit } from '@angular/core';
import { ProdutoInsert } from '../produtoInsert.model';
import { Categoria } from 'src/app/model/categoria.model';
import { MatDialog } from '@angular/material/dialog';
import { CategoriaService } from 'src/app/service/categoria.service';
import { ProductService } from 'src/app/service/product.service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-criar-produto',
  templateUrl: './criar-produto.component.html',
  styleUrls: ['./criar-produto.component.css']
})
export class CriarProdutoComponent implements OnInit {

  public categorias: Categoria[] = [];

  public idCategoria?: number;

  private imagem?: File;

  public tipo: any[] = [
      { label: 'Cachorro', value: 0 },
      { label: 'Gato', value: 1 }
  ];

  public selectedTipo?: number;

  public produto: ProdutoInsert = {
    id: 0,
    nome: '',
    descricao: '',  
    preco: 0,
    tipo: ''
  };

  constructor(private dialog: MatDialog, private categoriaService: CategoriaService,
    private service: ProductService,
    private snack: MatSnackBar, private spinner: NgxSpinnerService) { }
  
    ngOnInit(): void {
      this.listAll();
    }
  
    public close() {
      this.dialog.closeAll();
    }
  
    public listAll(): void {
      this.categoriaService.listAll().subscribe(data => {
        this.categorias = data;
      })
    }
  
    public create(): void {  
      this.spinner.show();  
      this.service.create(this.produto).subscribe(data => {
          
          this.produto = data;
          const idSelecionado = this.idCategoria;
          this.message("Produto Criado com sucesso! ");
          this.dialog.closeAll();
          this.spinner.hide();
          
          if (idSelecionado) {
            this.service.associarProduto(idSelecionado, this.produto.id).subscribe();
          }
          
          const img = this.imagem;
          if (img) {
            this.service.uploadImage(this.produto.id, img).subscribe(() => {
              this.message("Imagem enviada com sucesso!");
            }, (err: HttpErrorResponse) => {
              this.addMessageErrorFile(err); 
              this.spinner.hide();
            });
          }
          
        }, err => {
          for (const error of err.error.errors) {
            this.addMessageError(error.fieldName, error.message);
            this.spinner.hide();
          }
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
  
    public messageImg(msg: string) {
      this.snack
        .open(`${msg}`, 'Erro', {
          horizontalPosition: 'right',
          verticalPosition: 'top',
          duration: 8000
      })
    }
  
    public addMessageError(fieldName: string, errorMessage: string) {
      this.message(`${fieldName}: ${errorMessage}`);
    }
  
    public addMessageErrorFile(err: HttpErrorResponse) {
      if (err.status === 400 && err.error) {
        this.messageImg("Formato não suportado, Somente imagens PNG e JPG são permitidas.");
      } 
    }
  
    public onFileChange(event: any): void {
      if (event.target.files.length > 0) {
        this.imagem = event.target.files[0];
      }
    }
  
}
