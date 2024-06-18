import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { MaterialModule } from './material.model';

import { FormsModule } from '@angular/forms';

import { NavComponent } from './components/template/nav/nav.component';
import { HelloComponent } from './components/template/hello/hello.component';
import { HomeComponent } from './components/template/home/home.component';


@NgModule({
  declarations: [
    AppComponent,
    NavComponent,
    HelloComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
