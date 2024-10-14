import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth.service';
import { CriarCategoriaComponent } from '../../categoria/criar-categoria/criar-categoria.component';
import { GerenciarCategoriasComponent } from '../../categoria/gerenciar-categorias/gerenciar-categorias.component';
import { GerenciarUsuariosComponent } from '../../usuarios/gerenciar-usuarios/gerenciar-usuarios.component';
import { CriarProdutoComponent } from '../../produtos/criar-produto/criar-produto.component';
import { GerenciarProdutosComponent } from '../../produtos/gerenciar-produtos/gerenciar-produtos.component';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  constructor(private auth: AuthService, private router: Router, private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  public sair(): void {
    this.auth.logout(); 
    this.router.navigate(['']);
  }

  public inicio(): void {
    this.router.navigate(['']);
  }

  public criarCategoria(): void {
    this.dialog.open(CriarCategoriaComponent);
  }

  public gerenciarCategorias(): void {
    this.dialog.open(GerenciarCategoriasComponent);
  }

  public gerenciarUsuarios(): void {
    this.dialog.open(GerenciarUsuariosComponent);
  }

  public criarProduto(): void {
    this.dialog.open(CriarProdutoComponent);
  }

  public gerenciarProduto(): void {
    this.dialog.open(GerenciarProdutosComponent);
  }

}
