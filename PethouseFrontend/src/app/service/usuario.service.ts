import { HttpClient } from '@angular/common/http';
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
}
