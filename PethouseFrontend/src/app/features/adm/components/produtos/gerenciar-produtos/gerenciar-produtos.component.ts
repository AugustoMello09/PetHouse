import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Produto } from 'src/app/model/produto.modal';
import { ProductService } from 'src/app/service/product.service';
import { AtualizarProdutoComponent } from '../atualizar-produto/atualizar-produto.component';
import { PageEvent } from '@angular/material/paginator';


@Component({
  selector: 'app-gerenciar-produtos',
  templateUrl: './gerenciar-produtos.component.html',
  styleUrls: ['./gerenciar-produtos.component.css']
})
export class GerenciarProdutosComponent implements OnInit {

  public paged: Produto[] = [];
  public totalElements: number = 0;
  public pageSize: number = 5;
  public pageIndex: number = 0;

  constructor(private dialog: MatDialog, private service: ProductService) { }

  ngOnInit(): void {
    this.listAll();
  }

  public close() {
    this.dialog.closeAll();
  }

  public listAll(pageIndex: number = 0, pageSize: number = 5): void {
    this.service.findAllPaged(pageIndex, pageSize).subscribe(data => {
      this.paged = data.content;
      this.totalElements = data.totalElements;
    })
  }

  public pageEvent(event: PageEvent): void {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    this.listAll(this.pageIndex, this.pageSize);
  }

  public atualizarProduto(id: any): void {
    this.dialog.open(AtualizarProdutoComponent, {
      data: { id: id } 
    }).afterClosed().subscribe(() => {
      this.listAll();
    })
  }

}
