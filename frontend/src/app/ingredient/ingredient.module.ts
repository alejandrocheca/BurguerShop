import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IngredientComponent, IngredientEditComponent, IngredientListComponent, IngredientRecipeComponent, IngredientsAddComponent, IngredientViewComponent, INGREDIENT_COMPONENTS } from './components/ingredient.component';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import {TableModule} from 'primeng/table';

const routes: Routes = [
  { path: '', component: IngredientListComponent},
  { path: 'add', component: IngredientsAddComponent},
  { path: ':id/edit', component: IngredientEditComponent},
  { path: ':id', component: IngredientViewComponent},
];

@NgModule({
  declarations: [
    INGREDIENT_COMPONENTS, IngredientComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
    TableModule, CommonModule

  ],
  exports:[INGREDIENT_COMPONENTS, IngredientRecipeComponent]
})
export class IngredientModule { }
