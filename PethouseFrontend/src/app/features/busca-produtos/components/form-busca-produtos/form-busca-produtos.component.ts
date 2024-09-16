import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import { BuscaProdutosService } from 'src/app/service/busca-produtos.service';

@Component({
  selector: 'app-form-busca-produtos',
  templateUrl: './form-busca-produtos.component.html',
  styleUrls: ['./form-busca-produtos.component.css']
})
export class FormBuscaProdutosComponent implements OnInit {

  public mensagem: string = '';

  @ViewChild('resultList') resultList?: ElementRef;
  public results: any[] = [];

  constructor(private buscaService: BuscaProdutosService, private router: Router) { }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
       setTimeout(() => {  initFlowbite();})
      }
    });
  }

  public getSearchProdutos(): void {
    if (this.mensagem.length > 2) { 
      this.buscaService.getSearchResults(this.mensagem).subscribe(results => {
        this.results = results;
      });
    } else {
      this.results = [];
    }
  }

  @HostListener('document:click', ['$event'])
  public clickout(event: Event): void {
    const resultListContainsTarget = this.resultList?.nativeElement.contains(event.target);
    if (resultListContainsTarget) {
      return;
    }
    this.results = [];
  }

}
