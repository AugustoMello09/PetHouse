import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cidade } from '../model/cidade.model';
import { Estado } from '../model/estado.model';

@Injectable({
  providedIn: 'root'
})
export class EnderecoService {

  public base_url = 'https://viacep.com.br/ws/';
  public url_estados = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/';

  constructor(private http: HttpClient) { }

  public getConsultaCep(cep: string): Observable<any> {
    return this.http.get(`${this.base_url}${cep}/json`);
  }

  public getConsultaEstados(): Observable<Estado[]> {
    const url = `${this.url_estados}`
    return this.http.get<Estado[]>(url);
  }

  public getConsultaCidades(selectedUf: number): Observable<Cidade[]> {
    const url = `${this.url_estados}${selectedUf}/municipios`
    return this.http.get<Cidade[]>(url);
  }

  public getCoordenadasCidade(cidade: string): Observable<any> {
    const url = `https://nominatim.openstreetmap.org/search?q=${cidade}&format=json&addressdetails=1&limit=1`;
    return this.http.get<any>(url);
  }
}
