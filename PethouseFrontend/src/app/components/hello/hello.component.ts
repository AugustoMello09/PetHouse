import { Component, OnInit } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent implements OnInit {

  isLoggedIn = false;
  tokenData: any;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.authService.token$.subscribe(token => {
      this.isLoggedIn = !!token;
      this.tokenData = token ? jwtDecode(token) : null;
    });
  }

}
