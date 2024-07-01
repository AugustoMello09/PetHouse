import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Plano } from '../model/plano.model';

@Injectable({
  providedIn: 'root'
})
export class PlanoService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public listAll(): Observable<Plano[]> {
    const url = `${this.baseUrl}/v1/plano`;
    return this.http.get<Plano[]>(url);
  }

  public findById(id: Number): Observable<Plano> {
    const url = `${this.baseUrl}/v1/plano/${id}`;
    return this.http.get<Plano>(url);
  }

  public comprarPlano(id: Number, idUsuario: String): Observable<Plano> {
    const url = `${this.baseUrl}/v1/plano/contrarPlano/${id}/idUsuario/${idUsuario}`;
    return this.http.post<Plano>(url, {});
  }
}
