import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { OrderEditComponent, OrderListComponent, OrdersAddComponent, OrderViewComponent, ORDER_COMPONENTS } from './components/order.component';
import { FormsModule } from '@angular/forms';


const routes: Routes = [
  { path: '', component: OrderListComponent},
  { path: 'add', component: OrdersAddComponent},
  { path: ':id/edit', component: OrderEditComponent},
  { path: ':id', component: OrderViewComponent},
];

@NgModule({
  declarations: [ORDER_COMPONENTS],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
    CommonModule
  ]
})
export class OrdersModule { }
