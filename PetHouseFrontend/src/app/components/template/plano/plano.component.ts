import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Plano } from 'src/app/model/plano.model';
import { PlanoService } from 'src/app/service/plano.service';
import { PlanoModalComponent } from '../modal/plano-modal/plano-modal.component';
import { AuthService } from 'src/app/service/auth.service';
import { jwtDecode } from 'jwt-decode';
import { AuthModalComponent } from '../modal/auth-modal/auth-modal.component';

@Component({
  selector: 'app-plano',
  templateUrl: './plano.component.html',
  styleUrls: ['./plano.component.css']
})
export class PlanoComponent implements OnInit {

  planos: Plano[] = [];

  planoSelecionadoId? : number;

  constructor(private service: PlanoService, private dialog: MatDialog, private auth: AuthService) { }

  ngOnInit(): void {
    this.list();
  }

  public list() {
    this.service.listAll().subscribe(plano => {
      this.planos = plano;
      console.log(plano);
    })
  }

  public selecionarPlano(id: number) {
    this.dialog.open(PlanoModalComponent, {
      data: { id: id }
    });
  }

  public comprarPlano(id: number) {
    const isAuthenticated = this.auth.isAuthenticated();
    if (!isAuthenticated) {
      this.dialog.open(AuthModalComponent);
    } else {
      const token = this.auth.obterToken();
      if (token) {
        const decodedToken: any = jwtDecode(token);
        const userId = decodedToken.id; 
        this.service.comprarPlano(id, userId).subscribe();
      }
    }
  }

}
