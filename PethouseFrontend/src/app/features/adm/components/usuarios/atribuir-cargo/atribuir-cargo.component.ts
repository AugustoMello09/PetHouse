import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgxSpinnerService } from 'ngx-spinner';
import { Cargo } from 'src/app/model/cargo.model';
import { Usuario } from 'src/app/model/usuario.model';
import { CargoService } from 'src/app/service/cargo.service';
import { UsuarioService } from 'src/app/service/usuario.service';

@Component({
  selector: 'app-atribuir-cargo',
  templateUrl: './atribuir-cargo.component.html',
  styleUrls: ['./atribuir-cargo.component.css']
})
export class AtribuirCargoComponent implements OnInit {

  public cargos: Cargo[] = [];

  public selectedRoleId?: number;

  public usuario: Usuario = {
    id: '',
    nome: '',
    email: '',
    cpfOrCnpj: '',
    cargos: [
      {
        id: 0,
        authority: ''
      }
    ]
  }

  constructor(public dialogRef: MatDialogRef<AtribuirCargoComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: any },
    private service: CargoService, private usuarioService: UsuarioService,
    private spinner: NgxSpinnerService) { }

  ngOnInit(): void {
    this.listAll();
    this.getUsuarioById(this.data.id);
  }

  public close(): void {
    this.dialogRef.close(true);
  }

  public listAll(): void {
    this.service.listAll().subscribe(res => {
      this.cargos = res;
    });
  }

  public getUsuarioById(id: number): void {
    this.usuarioService.findById(id).subscribe(res => {
        this.usuario = res;
    });
  }

  public atribuirCargo(): void {
    if (this.selectedRoleId) {
      this.spinner.show();
      this.service.atribuirCargo(this.usuario.id, [{ id: this.selectedRoleId }]).subscribe(() => {
        this.spinner.hide();
        this.dialogRef.close();
      }, () => {
        this.spinner.hide();
      });
    }
  }

}
