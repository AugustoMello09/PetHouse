import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Categoria } from '../model/categoria.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  baseUrl: string = environment.baseUrl;

  private categoriaSubject: BehaviorSubject<Categoria | null> = new BehaviorSubject<Categoria | null>(null);
  private categoriasSubject: BehaviorSubject<Categoria[] | null> = new BehaviorSubject<Categoria[] | null>(null);

  constructor(private http: HttpClient) { }

  get categoria$(): Observable<Categoria | null> {
    return this.categoriaSubject.asObservable();
  }

  get categorias$(): Observable<Categoria[] | null> {
    return this.categoriasSubject.asObservable();
  }

  create(categoria: Categoria): Observable<Categoria> {
    const url = `${this.baseUrl}/pethouse/v1/categoria`
    return this.http.post<Categoria>(url, categoria).pipe(
      tap(carrinho => this.categoriaSubject.next(carrinho))
    );
  }

  listAll(): Observable<Categoria[]> {
    const url = `${this.baseUrl}/pethouse/v1/categoria`;
    return this.http.get<Categoria[]>(url).pipe(
      tap(categorias => this.categoriasSubject.next(categorias))
    );
  }

  update(categoria: Categoria): Observable<Categoria> {
    const url = `${this.baseUrl}/pethouse/v1/categoria/${categoria.id}`;
    return this.http.put<Categoria>(url, categoria).pipe(
      tap(carrinho => this.categoriaSubject.next(carrinho))
    );
  }

  findById(id: any): Observable<Categoria>{
    const url = `${this.baseUrl}/pethouse/v1/categoria/${id}`;
    return this.http.get<Categoria>(url);
  }

  delete(id: any): Observable<void>{
    const url = `${this.baseUrl}/pethouse/v1/categoria/${id}`;
    return this.http.delete<void>(url);
  }
}
