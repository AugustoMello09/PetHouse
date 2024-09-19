import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { jwtDecode } from 'jwt-decode';
import { AuthModalComponent } from 'src/app/modals/auth-modal/auth-modal.component';
import { CarrinhoInfo } from 'src/app/model/carrinhoInfo.model';
import { ItemCarrinho } from 'src/app/model/itemCarrinho.model';
import { Produto } from 'src/app/model/produto.modal';
import { AuthService } from 'src/app/service/auth.service';
import { CarrinhoService } from 'src/app/service/carrinho.service';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  @Input() produto?: Produto;

  produtoSelecionadoId?: number;
  
  carrinho: CarrinhoInfo = {
    id: '',
    itemsCarrinho: [
      {
        produtoId: 0,
        quantidade: 1,
        nome: '',
        preco: 0,
        imgProduto: ''
      },
    ],
    valorTotalCarrinho: 0
  };

  constructor(private auth: AuthService, private dialog: MatDialog,
    private snack: MatSnackBar, private service: CarrinhoService
  ) { }

  ngOnInit(): void {
  }

  public addCarrinho(produtoId?: number): void {  
    const isAuthenticated = this.auth.isAuthenticated();
    if (!isAuthenticated) {
      this.dialog.open(AuthModalComponent);
    } else {
      const token = this.auth.obterToken();
      if (token) {
        const decodedToken: any = jwtDecode(token);
        this.carrinho.id = decodedToken.carrinhoId
        const itemCarrinho: ItemCarrinho = { produtoId, quantidade: 1, nome: '', preco: 0, imgProduto: ''};
        this.carrinho.itemsCarrinho = [itemCarrinho];       
        this.service.addCarrinho(this.carrinho).subscribe(() => {
          this.message("Item adicionado no carrinho com sucesso! ")
        });
      }
    }
  }

  public message(msg: string) {
    this.snack
      .open(`${msg}`, 'OK', {
        horizontalPosition: 'center',
        verticalPosition: 'bottom',
        duration: 8000
    })
  }

  public addMessageError(fieldName: string, errorMessage: string) {
    this.message(`${fieldName}: ${errorMessage}`);
  }

}
