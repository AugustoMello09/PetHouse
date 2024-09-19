import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/layout/navbar/navbar.component';
import { FormBuscaProdutosComponent } from './features/busca-produtos/components/form-busca-produtos/form-busca-produtos.component';
import { InformacoesComponent } from './features/perfil/components/informacoes/informacoes.component';

import { AuthService } from './service/auth.service';
import { AuthInterceptor } from './auth.interceptor';
import { MaterialModule } from './app-material.model';
import { LoginComponent } from './modals/login/login.component';
import { NgxSpinnerModule } from 'ngx-spinner';
import { RegistroComponent } from './modals/registro/registro.component';
import { SacolaComponent } from './features/sacola/components/sacola/sacola.component';
import { SlideComponent } from './components/slide/slide.component';
import { HelloComponent } from './components/hello/hello.component';
import { PlanosComponent } from './components/planos/planos.component';
import { PlanoModalComponent } from './modals/plano-modal/plano-modal.component';
import { AuthModalComponent } from './modals/auth-modal/auth-modal.component';
import { LeaftModule } from './app-leaft.model';
import { PetsComponent } from './components/pets/pets.component';
import { MapaComponent } from './modals/mapa/mapa.component';
import { AboutComponent } from './components/about/about.component';
import { ProductComponent } from './components/product/product.component';
import { CardComponent } from './features/produtos/components/card/card.component';
import { ContactComponent } from './components/contact/contact.component';
import { FooterComponent } from './components/layout/footer/footer.component';



@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    FormBuscaProdutosComponent,
    InformacoesComponent,
    LoginComponent,
    RegistroComponent,
    SacolaComponent,
    SlideComponent,
    HelloComponent,
    PlanosComponent,
    PlanoModalComponent,
    AuthModalComponent,
    PetsComponent,
    MapaComponent,
    AboutComponent,
    ProductComponent,
    CardComponent,
    ContactComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MaterialModule,
    NgxSpinnerModule,
    LeaftModule
  ],
  providers: [
    AuthService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
