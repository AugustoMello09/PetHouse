import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Usuario } from 'src/app/model/usuario.model';
import { UsuarioService } from 'src/app/service/usuario.service';
import { AtualizarUsuarioComponent } from '../atualizar-usuario/atualizar-usuario.component';
import { AtribuirCargoComponent } from '../atribuir-cargo/atribuir-cargo.component';

@Component({
  selector: 'app-gerenciar-usuarios',
  templateUrl: './gerenciar-usuarios.component.html',
  styleUrls: ['./gerenciar-usuarios.component.css']
})
export class GerenciarUsuariosComponent implements OnInit {

  public displayedColumns: string[] = ['id', 'nome', 'email', 'acoes'];
  public dataSource: Usuario[] = [];

  constructor(private dialog: MatDialog, private service: UsuarioService) { }

  ngOnInit(): void {
    this.listAll();
  }

  public listAll() {
    this.service.findAllPaged(0, 5).subscribe(results => {
      this.dataSource = results.content;
    })
  }

  public close() {
    this.dialog.closeAll();
  }

  public editUsuario(id: any): void {
    this.dialog.open(AtualizarUsuarioComponent, {
      data: { id: id }
    }).afterClosed().subscribe(() => {
      this.listAll();
    });
  }

  public atribuirCargo(id: any) {
    this.dialog.open(AtribuirCargoComponent, {
      data: { id: id }
    }).afterClosed().subscribe(() => {
      this.listAll();
    });
  }

}
