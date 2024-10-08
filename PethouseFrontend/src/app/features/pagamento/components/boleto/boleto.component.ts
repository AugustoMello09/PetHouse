import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as JsBarcode from 'jsbarcode';

@Component({
  selector: 'app-boleto',
  templateUrl: './boleto.component.html',
  styleUrls: ['./boleto.component.css']
})
export class BoletoComponent implements OnInit {

  preco!: '';
  dueDate!: '';
  nossoNumero!: '';
  identificationField!: '';
  description!: '';
  barCode!: '';
  dataCriacao!: '';
  invoiceNumber!: string;
  formattedBarCode!: string;
  nome!: '';
  cpfCnpj!: '';

  constructor(private router: Router) { }

  ngOnInit(): void {
    const storedBoletoData = localStorage.getItem('boletoData');
    if (storedBoletoData) {
       const boletoData = JSON.parse(storedBoletoData);
      this.preco = boletoData.preco;
      this.dueDate = boletoData.dueDate;
      this.nossoNumero = boletoData.nossoNumero;
      this.identificationField = boletoData.identificationField;
      this.description = boletoData.description
      this.barCode = boletoData.barCode;
      this.dataCriacao = boletoData.dataCriacao;
      this.invoiceNumber = this.formatNDocument(boletoData.invoiceNumber);
      this.nome = boletoData.nome;
      this.cpfCnpj = boletoData.cpfCnpj;
      this.formattedBarCode = this.formatBarCode(this.identificationField);
      this.generateBarcode();
    }
  }

  generateBarcode() {
    JsBarcode('#barcode', this.barCode, {
      format: 'CODE128',
      width: 2,
      height: 50,
      displayValue: false
    });
  }

  formatBarCode(barCode: string): string {
    if (barCode.length === 47) { 
      return `${barCode.substring(0, 5)}.${barCode.substring(5, 10)} ${barCode.substring(10, 15)}.${barCode.substring(15, 21)} ${barCode.substring(21, 26)}.${barCode.substring(26, 32)} ${barCode.substring(32, 33)} ${barCode.substring(33)}`;
    } else {
      return barCode; 
    }
  }

  formatNDocument(document: string): string {
    return document.replace(/^0+/, '');
  }

  copiarNumeroBoleto(): void {
    if (this.formattedBarCode) {
      navigator.clipboard.writeText(this.formattedBarCode).then(() => {
        alert('Código de barras copiado para a área de transferência!');
      }).catch(err => {
        console.error('Erro ao copiar o código: ', err);
      });
    }
  }

  voltar(): void {
    localStorage.removeItem('boletoData');
    this.router.navigate(['pagamento']);
  }

}
