import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Produto } from '../model/produto.modal';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  baseUrl: string = environment.baseUrl;

  private produtoSubject: BehaviorSubject<Produto[]> = new BehaviorSubject<Produto[]>([]);

  constructor(private http: HttpClient) { }

  get produto$(): Observable<Produto[]> {
    return this.produtoSubject.asObservable();
  }

  public findAllPaged(page: number, size: number): Observable<any> {
    let params = new HttpParams();
    params = params.set('page', page.toString());
    params = params.set('size', size.toString());
    params = params.set('sort', 'id,desc');
    return this.http.get<any>(this.baseUrl + '/pethouse/v1/produto', { params })
    .pipe(
      tap(response => this.produtoSubject.next(response.content)) 
    );
  }

  public findById(id: number): Observable<Produto> {
    const url = `${this.baseUrl}/pethouse/v1/produto/${id}`;
    return this.http.get<Produto>(url);
  }
}
