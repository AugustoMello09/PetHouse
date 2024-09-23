import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { MeuCarrinhoComponent } from './features/carrinho/components/meu-carrinho/meu-carrinho.component';
import { AuthGuard } from './guards/auth.guard';
import { CheckoutComponent } from './features/pagamento/components/checkout/checkout.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'meuCarrinho', component: MeuCarrinhoComponent, canActivate: [AuthGuard] },
  { path: 'pagamento', component: CheckoutComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
