import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Pagamento } from '../features/pagamento/components/checkout/pagamento.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  baseUrl: string = environment.baseUrl;

  constructor(private http: HttpClient) { }

  obterPagamento(idCarrinho: string): Observable<Pagamento> {
    const url = `${this.baseUrl}/payment/v1/payment/${idCarrinho}`;
    return this.http.get<Pagamento>(url);
  }
}
