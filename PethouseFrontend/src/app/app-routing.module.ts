import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { MeuCarrinhoComponent } from './features/carrinho/components/meu-carrinho/meu-carrinho.component';
import { AuthGuard } from './guards/auth.guard';
import { CheckoutComponent } from './features/pagamento/components/checkout/checkout.component';
import { PixComponent } from './features/pagamento/components/pix/pix.component';
import { BoletoComponent } from './features/pagamento/components/boleto/boleto.component';
import { CartaoComponent } from './features/pagamento/components/cartao/cartao.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'meuCarrinho', component: MeuCarrinhoComponent, canActivate: [AuthGuard] },
  { path: 'pagamento', component: CheckoutComponent, canActivate: [AuthGuard] },
  { path: 'pix', component: PixComponent, canActivate: [AuthGuard] },
  { path: 'boleto', component: BoletoComponent, canActivate: [AuthGuard] },
  { path: 'cartao', component: CartaoComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
