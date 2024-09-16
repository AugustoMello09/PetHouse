import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { jwtDecode } from 'jwt-decode';
import { BehaviorSubject, Observable, tap, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public baseUrl: string = environment.baseUrl;
  public loginUrl: string = `${this.baseUrl}/auth/v1/auth/login`;
  public jwtHelper: JwtHelperService = new JwtHelperService();
  public refreshTokenUrl: string = `${this.baseUrl}/auth/v1/auth/refresh-token`;

  private tokenSubject = new BehaviorSubject<string | null>(this.obterToken());
  private authStateSubject = new BehaviorSubject<boolean>(this.isAuthenticated());

  constructor(private http: HttpClient) { }

  public tentarLogar(email: string, senha: string): Observable<any> {
    const data = { email, senha };
    const headers = { 'Content-Type': 'application/json' };
    return this.http.post<any>(this.loginUrl, data, { headers }).pipe(
      tap(response => {
        const token = response?.accessToken;
        if (token) {
          this.setToken(token); 
        }
      })
    );
  }

  public setToken(token: string): void {
    localStorage.setItem('token_access', JSON.stringify({ accessToken: token }));
    this.tokenSubject.next(token);
    this.authStateSubject.next(true);
  }

  public logout(): void {
    localStorage.removeItem('token_access');
    this.tokenSubject.next(null);
    this.authStateSubject.next(false); 
  }

  public obterToken(): string | null {
    const tokenString = localStorage.getItem('token_access');
    if (tokenString) {
      const token = JSON.parse(tokenString).accessToken;
      return token;
    }
    return null;
  }

  public isAuthenticated(): boolean {
    const token = this.obterToken();
    return token ? !this.jwtHelper.isTokenExpired(token) : false;
  }

  public getTokenData(): any {
    const token = this.obterToken();
    return token ? jwtDecode(token) : null;
  }

  get token$(): Observable<string | null> {
    return this.tokenSubject.asObservable();
  }

  get authState$(): Observable<boolean> {
    return this.authStateSubject.asObservable();
  }
  
  public refreshToken(): Observable<any> {
    const token = this.obterToken();
    localStorage.removeItem('token_access');
    if (token) {
      const body = { token: token };
      return this.http.post<any>(this.refreshTokenUrl, body);
    }
    return throwError('Token não encontrado');
  }
}
