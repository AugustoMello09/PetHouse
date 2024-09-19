import { Component, HostListener, OnInit } from '@angular/core';
import { debounceTime, Subject } from 'rxjs';
import { Produto } from 'src/app/model/produto.modal';
import { ProductService } from 'src/app/service/product.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  produtos: Produto[] = [];

  private resizeSubject: Subject<number> = new Subject();

  currentPage: number = 0;
  totalPages: number = 0;
  pageSize: number = 3;

  
  isLoading: boolean = false;

  constructor(private service: ProductService) { }

  ngOnInit(): void {
    this.updatePageSize(window.innerWidth);
    this.readProducts();
    this.resizeSubject.pipe(debounceTime(300)).subscribe(width => {
      this.updatePageSize(width);
      this.readProducts();
    });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.updatePageSize(event.target.innerWidth);
    this.readProducts();
  }

  updatePageSize(width: number) {
    if (width <= 800) {
      this.pageSize = 1;
    } else if (width <= 1280) {
      this.pageSize = 2;
    } else {
      this.pageSize = 3;
    }
  }

  readProducts() {
    this.service.findAllPaged(this.currentPage, this.pageSize).subscribe(res => {
      this.produtos = res.content;
      this.totalPages = res.totalPages;
      if (this.produtos.length == 0) { 
        this.isLoading = true;
      }
    },
    () => {
      this.isLoading = true;
    });
  }
  
  
  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.readProducts();
    }
  }

  previousPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.readProducts();
    }
  }

}
