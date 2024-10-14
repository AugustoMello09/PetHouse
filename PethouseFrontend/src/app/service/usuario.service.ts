import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { usuarioRegistro } from '../modals/registro/usuarioRegistro.model';
import { Observable } from 'rxjs';
import { Usuario } from '../model/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public create(usuario: usuarioRegistro): Observable<usuarioRegistro> {
    const url = `${this.baseUrl}/pethouse/v1/usuario`;
    return this.http.post<usuarioRegistro>(url, usuario);
  }

  public findById(id: any): Observable<Usuario> {
    const url = `${this.baseUrl}/pethouse/v1/usuario/${id}`;
    return this.http.get<Usuario>(url);
  }

  public findAllPaged(page: number, size: number): Observable<any> {
    let params = new HttpParams();
    params = params.set('page', page.toString());
    params = params.set('size', size.toString());
    params = params.set('sort', 'nome,desc');
    return this.http.get<any>(this.baseUrl + '/pethouse/v1/usuario', { params });
  }

  public update(usuario: Usuario): Observable<Usuario> {
    const url = `${this.baseUrl}/pethouse/v1/usuario/${usuario.id}`;
    return this.http.put<Usuario>(url, usuario);
  }
}
