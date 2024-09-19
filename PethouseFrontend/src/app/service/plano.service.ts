import { HttpClient, HttpParams } from '@angular/common/http';
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

  public listAll(page: number, size: number): Observable<any> {
    let params = new HttpParams();
    params = params.set('page', page.toString());
    params = params.set('size', size.toString());
    params = params.set('sort', 'id,asc');
    return this.http.get<any>(this.baseUrl + '/pethouse/v1/plano', { params });
  }

  public findById(id: Number): Observable<Plano> {
    const url = `${this.baseUrl}/pethouse/v1/plano/${id}`;
    return this.http.get<Plano>(url);
  }

  public comprarPlano(id: Number, idUsuario: String): Observable<Plano> {
    const url = `${this.baseUrl}/pethouse/v1/plano/contrarPlano/${id}/idUsuario/${idUsuario}`;
    return this.http.post<Plano>(url, {});
  }
  
}
