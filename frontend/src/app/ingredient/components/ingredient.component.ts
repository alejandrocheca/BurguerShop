import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { IngredientViewModelService } from '../services/ingredient.service';

@Component({
  selector: 'app-ingredient',
  templateUrl: './tmpl-main.component.html',
  styleUrls: ['./component.component.css']
})
export class IngredientComponent implements OnInit, OnDestroy {
  constructor(protected vm: IngredientViewModelService) { }
  public get VM(): IngredientViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy():void{
    this.vm.clear();
  }
}
@Component({
  selector: 'app-ingredient-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./component.component.css']
})
export class IngredientListComponent implements OnInit {
  constructor(protected vm: IngredientViewModelService) { }
  public get VM(): IngredientViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
}
@Component({
  selector: 'app-ingredient-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./component.component.css']
})
export class IngredientViewComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: IngredientViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): IngredientViewModelService { return this.vm; }
  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params?.get('id')??'';
        if(id){
          this.vm.view(id);
        }else{
        }
      }
    )
  }
  ngOnDestroy(): void {
    this.obs$.unsubscribe();
  }
}
@Component({
  selector: 'app-ingredient-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class IngredientEditComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: IngredientViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): IngredientViewModelService { return this.vm; }
  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params?.get('id')??'';
        if(id){
          this.vm.edit(id);
        }else{
        }
      }
    )
  }
  ngOnDestroy(): void {
    this.obs$.unsubscribe();
  }
}
@Component({
  selector: 'app-ingredient-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class IngredientsAddComponent implements OnInit {
  constructor(protected vm: IngredientViewModelService) { }
  public get VM(): IngredientViewModelService { return this.vm; }
  ngOnInit(): void {
    this.VM.add();
  }
}
@Component({
  selector: 'app-ingredient-recipe',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class IngredientRecipeComponent implements OnInit {
  constructor(protected vm: IngredientViewModelService) { }
  public get VM(): IngredientViewModelService { return this.vm; }
  ngOnInit(): void {
    this.VM.list();
  }
}
export const INGREDIENT_COMPONENTS = [
  IngredientComponent, IngredientListComponent,IngredientViewComponent,
  IngredientEditComponent, IngredientsAddComponent,IngredientRecipeComponent,
];


