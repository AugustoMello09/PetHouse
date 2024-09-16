import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BuscaProdutosService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  public getSearchResults(mensagem: String): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/pethouse/v1/produto/buscaPorNome/nomeProduto/${mensagem}`);
  }
  
}
