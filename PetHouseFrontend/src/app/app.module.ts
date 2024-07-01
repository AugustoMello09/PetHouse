import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MaterialModule } from './material.model';

import { FormsModule } from '@angular/forms';

import { NavComponent } from './components/template/nav/nav.component';
import { HelloComponent } from './components/template/hello/hello.component';
import { HomeComponent } from './components/template/home/home.component';
import { LoginComponent } from './components/template/modal/login/login.component';
import { RegistroComponent } from './components/template/modal/registro/registro.component';
import { PlanoComponent } from './components/template/plano/plano.component';
import { PlanoModalComponent } from './components/template/modal/plano-modal/plano-modal.component';
import { AuthService } from './service/auth.service';
import { AuthInterceptor } from './auth.interceptor';
import { AuthModalComponent } from './components/template/modal/auth-modal/auth-modal.component';




@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HelloComponent,
    HomeComponent,
    LoginComponent,
    RegistroComponent,
    PlanoComponent,
    PlanoModalComponent,
    AuthModalComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass:  AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
