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
import { MeuCarrinhoComponent } from './features/carrinho/components/meu-carrinho/meu-carrinho.component';
import { AuthInterceptor } from './interceptors/auth.interceptor';
import { RefreshTokenInterceptor } from './interceptors/refresh-token.interceptor';
import { CheckoutComponent } from './features/pagamento/components/checkout/checkout.component';
import { EmailValidatorDirective } from './directives/email-validator.directive';
import { MaskDirective } from './directives/mask.directive';
import { ValidandoCepDirective } from './directives/validando-cep.directive';
import { NavbarcheckoutComponent } from './features/pagamento/components/layout/navbarcheckout/navbarcheckout.component';
import { PixComponent } from './features/pagamento/components/pix/pix.component';
import { BoletoComponent } from './features/pagamento/components/boleto/boleto.component';
import { CartaoComponent } from './features/pagamento/components/cartao/cartao.component';
import { AdmHomeComponent } from './features/adm/components/adm-home/adm-home.component';
import { SidenavComponent } from './features/adm/components/layout/sidenav/sidenav.component';
import { AtualizarCategoriaComponent } from './features/adm/components/categoria/atualizar-categoria/atualizar-categoria.component';
import { CriarCategoriaComponent } from './features/adm/components/categoria/criar-categoria/criar-categoria.component';
import { GerenciarCategoriasComponent } from './features/adm/components/categoria/gerenciar-categorias/gerenciar-categorias.component';
import { AtribuirCargoComponent } from './features/adm/components/usuarios/atribuir-cargo/atribuir-cargo.component';
import { AtualizarUsuarioComponent } from './features/adm/components/usuarios/atualizar-usuario/atualizar-usuario.component';
import { GerenciarUsuariosComponent } from './features/adm/components/usuarios/gerenciar-usuarios/gerenciar-usuarios.component';
import { AtualizarProdutoComponent } from './features/adm/components/produtos/atualizar-produto/atualizar-produto.component';
import { CriarProdutoComponent } from './features/adm/components/produtos/criar-produto/criar-produto.component';
import { GerenciarProdutosComponent } from './features/adm/components/produtos/gerenciar-produtos/gerenciar-produtos.component';



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
    FooterComponent,
    MeuCarrinhoComponent,
    CheckoutComponent,
    EmailValidatorDirective,
    MaskDirective,
    ValidandoCepDirective,
    NavbarcheckoutComponent,
    PixComponent,
    BoletoComponent,
    CartaoComponent,
    AdmHomeComponent,
    SidenavComponent,
    AtualizarCategoriaComponent,
    CriarCategoriaComponent,
    GerenciarCategoriasComponent,
    AtribuirCargoComponent,
    AtualizarUsuarioComponent,
    GerenciarUsuariosComponent,
    AtualizarProdutoComponent,
    CriarProdutoComponent,
    GerenciarProdutosComponent
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
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: RefreshTokenInterceptor, 
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule { }
