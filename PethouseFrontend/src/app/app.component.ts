import { Component } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { initFlowbite } from 'flowbite';
import * as EmailJS from '@emailjs/browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'PethouseFrontend';

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
       setTimeout(() => {  initFlowbite();})
      }
    });
    EmailJS.init('VTT9bcqQ7mURlEBVe');
  }
}
