import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  baseUrl: string = environment.baseUrl;
  loginUrl: string = environment.baseUrl + '/v1/auth/login';
  jwtHelper: JwtHelperService = new JwtHelperService();


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

  obterToken() {
    const tokenString = localStorage.getItem('token_access');
    if (tokenString) {
      const token = JSON.parse(tokenString).accessToken;
      return token;
    }
    return null;
  }

  isAuthenticated(): boolean {
    const token = this.obterToken();
    if (token) {
      const expired = this.jwtHelper.isTokenExpired(token)
      return !expired;
    }
    return false;
  }

  getTokenData() {
    const token =  this.obterToken();
    if (token) {
      const decodedToken = jwtDecode(token);
      return decodedToken;
    }
    return null;
  }
}
