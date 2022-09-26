
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HamburguerEditComponent, HamburguerListAdminComponent, HamburguerListComponent, HamburguersAddComponent, HamburguerViewComponent, HAMBURGUER_COMPONENTS } from '../hamburguer/components/hamburguer.component';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { IngredientModule } from '../ingredient';
import { IngredientRecipeComponent } from '../ingredient/components/ingredient.component';

const routes: Routes = [
  { path: '', component: HamburguerListComponent},
  { path: 'admin', component: HamburguerListAdminComponent},
  { path: 'add', component: HamburguersAddComponent},
  { path: ':id/edit', component: HamburguerEditComponent},
  { path: 'admin/:id/edit', component: HamburguerEditComponent},
  { path: ':id', component: HamburguerViewComponent},
  { path: 'admin/:id', component: HamburguerViewComponent},

];
@NgModule({
  declarations: [
    HAMBURGUER_COMPONENTS
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
  ],
  exports:[HAMBURGUER_COMPONENTS]
})
export class HamburguerModule { }
