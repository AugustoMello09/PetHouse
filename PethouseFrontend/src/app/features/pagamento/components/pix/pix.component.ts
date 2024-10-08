import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, NavigationStart, Router } from '@angular/router';

@Component({
  selector: 'app-pix',
  templateUrl: './pix.component.html',
  styleUrls: ['./pix.component.css']
})
export class PixComponent implements OnInit {

  encodedImage!: string;
  payload!: string;
  preco!: number;
  dueDate!: string;

  constructor(private router: Router) {}

  ngOnInit(): void {
    const pixDataString = localStorage.getItem('pixData');
    if (pixDataString) {
      const pixData = JSON.parse(pixDataString);
      this.encodedImage = pixData.encodedImage;
      this.payload = pixData.payload;
      this.preco = pixData.preco;
      this.dueDate = pixData.dueDate;
    } 

    this.router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (event.navigationTrigger === 'popstate') {
          localStorage.removeItem('pixData');
          this.router.navigate(['/']);
        }
      }
    });
  }

  copiarCodigoPix(): void {
    const input = document.getElementById('pix-code') as HTMLInputElement;
    if (input) {
      input.select();
      document.execCommand('copy');
      alert('Código PIX copiado!');
    }
  }

  voltar(): void {
    localStorage.removeItem('pixData');
    this.router.navigate(['pagamento']);
  }

}
