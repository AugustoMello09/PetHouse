import { Historico } from './../model/historico.model';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { BehaviorSubject, Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HistoricoService {

  baseUrl: string = environment.baseUrl;

  private HistoricoSubject: BehaviorSubject<Historico | null> = new BehaviorSubject<Historico | null>(null);

  constructor(private http: HttpClient) { }

  get historico$(): Observable<Historico | null> {
    return this.HistoricoSubject.asObservable();
  }

  public findById(id: any): Observable<Historico> {
    const url = `${this.baseUrl}/pethouse/v1/historico/${id}`;
    return this.http.get<Historico>(url).pipe(
      tap(historic => this.HistoricoSubject.next(historic))
    );
  }
}
