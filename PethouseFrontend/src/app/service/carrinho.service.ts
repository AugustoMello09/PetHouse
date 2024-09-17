import { Injectable } from '@angular/core';
import { CarrinhoInfo } from '../model/carrinhoInfo.model';
import { BehaviorSubject, Observable, switchMap, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CarrinhoService {

  baseUrl: string = environment.baseUrl;

  private carrinhoSubject: BehaviorSubject<CarrinhoInfo | null> = new BehaviorSubject<CarrinhoInfo | null>(null);

  constructor(private http: HttpClient) { }

  get carrinho$(): Observable<CarrinhoInfo | null> {
    return this.carrinhoSubject.asObservable();
  }

  atualizarCarrinho(carrinho: CarrinhoInfo) {
    this.carrinhoSubject.next(carrinho);
  }

  findById(carrinhoId: string): Observable<CarrinhoInfo> {
    const url = `${this.baseUrl}/pethouse/v1/carrinho/${carrinhoId}`;
    return this.http.get<CarrinhoInfo>(url).pipe(
      tap(carrinho => this.carrinhoSubject.next(carrinho))
    );
  }

  addCarrinho(carrinho: CarrinhoInfo): Observable<CarrinhoInfo> {
    const url = `${this.baseUrl}/pethouse/v1/carrinho/adicionarAoCarrinho`;
    return this.http.post<CarrinhoInfo>(url, carrinho).pipe(
      tap(carrinho => this.carrinhoSubject.next(carrinho))
    );
  }

  removeItem(carrinhoId: string, produtoId: number): Observable<CarrinhoInfo> {
    const url = `${this.baseUrl}/pethouse/v1/carrinho/removerProduto/IdCarrinho/${carrinhoId}/IdProduto/${produtoId}`;
    return this.http.patch<void>(url, {}).pipe(
      switchMap(() => this.findById(carrinhoId)), 
      tap(carrinho => this.carrinhoSubject.next(carrinho)) 
    );
  }

  finalizar(carrinhoId: string): Observable<CarrinhoInfo> {
    const url = `${this.baseUrl}/pethouse/v1/carrinho/finalizarCarrinho/${carrinhoId}`;
    return this.http.patch<void>(url, {}).pipe(
      switchMap(() => this.findById(carrinhoId)), 
      tap(carrinho => this.carrinhoSubject.next(carrinho)) 
    );
    
  }
}
