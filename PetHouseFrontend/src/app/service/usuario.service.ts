import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { usuarioRegistro } from '../model/usuarioRegistro.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public create(usuario: usuarioRegistro): Observable<usuarioRegistro> {
    const url = `${this.baseUrl}/v1/usuario`
    return this.http.post<usuarioRegistro>(url, usuario);
  }
}
