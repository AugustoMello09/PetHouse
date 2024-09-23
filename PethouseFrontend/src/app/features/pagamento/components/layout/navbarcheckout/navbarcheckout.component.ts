import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbarcheckout',
  templateUrl: './navbarcheckout.component.html',
  styleUrls: ['./navbarcheckout.component.css']
})
export class NavbarcheckoutComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  public voltar(): void {
    this.router.navigate(['/meuCarrinho']);
  }

}
