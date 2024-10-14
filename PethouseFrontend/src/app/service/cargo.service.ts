import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Cargo } from '../model/cargo.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CargoService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public atribuirCargo(idUsuario: any, cargos: { id: number }[]): Observable<any> {
    const url = `${this.baseUrl}/pethouse/v1/usuario/atribuirCargo/${idUsuario}`;
    return this.http.patch<any>(url, { cargos });
  }

  public findById(id: number): Observable<Cargo> {
    const url = `${this.baseUrl}/pethouse/v1/cargo/${id}`;
    return this.http.get<Cargo>(url);
  }

  public listAll(): Observable<Cargo[]> {
    const url = `${this.baseUrl}/pethouse/v1/cargo`;
    return this.http.get<Cargo[]>(url);
  }
}
