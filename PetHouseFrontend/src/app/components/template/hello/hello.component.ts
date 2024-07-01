import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/service/auth.service';

@Component({
  selector: 'app-hello',
  templateUrl: './hello.component.html',
  styleUrls: ['./hello.component.css']
})
export class HelloComponent implements OnInit {

  tokenData: any;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.tokenData = this.authService.getTokenData();
  }

}
