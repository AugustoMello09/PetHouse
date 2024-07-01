import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { Plano } from 'src/app/model/plano.model';
import { AuthService } from 'src/app/service/auth.service';
import { PlanoService } from 'src/app/service/plano.service';
import { AuthModalComponent } from '../auth-modal/auth-modal.component';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-plano-modal',
  templateUrl: './plano-modal.component.html',
  styleUrls: ['./plano-modal.component.css']
})
export class PlanoModalComponent implements OnInit {

  plano!: Plano;

  constructor(private service: PlanoService,
    public dialogRef: MatDialogRef<PlanoModalComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: number }, private auth: AuthService, private dialog: MatDialog) { }

  ngOnInit(): void {
    this.findById(this.data.id);
  }

  findById(id: number) {
    this.service.findById(id).subscribe(plano => {
      this.plano = plano;
      console.log('Plano encontrado:', plano);
    });
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  public comprarPlano() {
    const isAuthenticated = this.auth.isAuthenticated();
    if (!isAuthenticated) {
      this.dialog.open(AuthModalComponent);
    } else {
      const token = this.auth.obterToken();
      if (token) {
        const decodedToken: any = jwtDecode(token);
        const userId = decodedToken.id; 
        this.service.comprarPlano(this.data.id, userId).subscribe();
      }
    }
  }

}
