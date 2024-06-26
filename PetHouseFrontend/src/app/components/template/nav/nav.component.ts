import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { SearchProdutosService } from 'src/app/service/search-produtos.service';
import { LoginComponent } from '../modal/login/login.component';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  mensagem: string = '';

  @ViewChild('resultList') resultList?: ElementRef;
  results: any[] = [];
  
  constructor(private buscaService: SearchProdutosService, private dialog: MatDialog) { }

  ngOnInit(): void {
  }

  public getSearchProdutos() {
    if (this.mensagem.length > 2) { 
      this.buscaService.getSearchResults(this.mensagem).subscribe(results => {
        console.log(results);
        this.results = results;
      });
    } else {
      this.results = [];
    }
  }

  @HostListener('document:click', ['$event'])
  clickout(event: Event) {
    const resultListContainsTarget = this.resultList?.nativeElement.contains(event.target);
    if (resultListContainsTarget) {
      return;
    }
    this.results = [];
  }

  open() {
    this.dialog.open(LoginComponent);
  }
}
