import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HamburguerListComponent } from './hamburger/components/hamburguer.component';
import { UserEditComponent } from './user/components/user.component';

const routes: Routes = [
  {path: '', component: HamburguerListComponent, pathMatch: 'full'},
  { path: 'users',loadChildren: () => import('./security/security.module').then(mod => mod.SecurityModule)},
  { path: 'orders',loadChildren: () => import('./orders/order.module').then(mod => mod.OrdersModule)},
  { path: 'hamburguers', loadChildren: () => import('./hamburger/hamburguer.module').then(mod => mod.HamburguerModule)},
  { path: 'ingredients', loadChildren: () => import('./ingredient/ingredient.module').then(mod => mod.IngredientModule)},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
