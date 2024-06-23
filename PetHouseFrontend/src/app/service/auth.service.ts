import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl: string = environment.baseUrl;
  loginUrl: string = environment.baseUrl + '/v1/auth/login';

  constructor(private http: HttpClient) { }

  tentarLogar(email: string, senha: string): Observable<any> {
    const data = {
      email: email,
      senha: senha
    };
    const headers = {
      'Content-Type': 'application/json'
    };
    return this.http.post(this.loginUrl, data, { headers });
  }
}
