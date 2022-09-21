import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { OrderViewModelService } from '../services/order.service';

@Component({
  selector: 'app-order',
  templateUrl: './tmpl-main.component.html',
  styleUrls: ['./component.component.css']
})
export class OrderComponent implements OnInit, OnDestroy {
  constructor(protected vm: OrderViewModelService) { }
  public get VM(): OrderViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy():void{
    this.vm.clear();
  }
}
@Component({
  selector: 'app-order-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./component.component.css']
})
export class OrderListComponent implements OnInit {
  constructor(protected vm: OrderViewModelService) { }
  public get VM(): OrderViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
}
@Component({
  selector: 'app-order-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./component.component.css']
})
export class OrderViewComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: OrderViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): OrderViewModelService { return this.vm; }
  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params?.get('id')??'';
        if(id){
          this.vm.view(id);
        }else{
        //  this.router.navigate(['/404.html'])
        }
      }
    )
  }
  ngOnDestroy(): void {
    this.obs$.unsubscribe();
  }
}
@Component({
  selector: 'app-order-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class OrderEditComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: OrderViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): OrderViewModelService { return this.vm; }
  ngOnInit(): void {
    this.obs$ = this.route.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params?.get('id')??'';
        if(id){
          this.vm.edit(id);
        }else{
        //  this.router.navigate(['/404.html'])
        }
      }
    )
  }
  ngOnDestroy(): void {
    this.obs$.unsubscribe();
  }
}
@Component({
  selector: 'app-order-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class OrdersAddComponent implements OnInit {
  constructor(protected vm: OrderViewModelService) { }
  public get VM(): OrderViewModelService { return this.vm; }
  ngOnInit(): void {
    this.VM.add();
  }
}
@Component({
  selector: 'app-order-recipe',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class OrderRecipeComponent implements OnInit {
  constructor(protected vm: OrderViewModelService) { }
  public get VM(): OrderViewModelService { return this.vm; }
  ngOnInit(): void {
    this.VM.list();
  }
}
export const ORDER_COMPONENTS = [
  OrderComponent, OrderListComponent,OrderViewComponent,
  OrderEditComponent, OrdersAddComponent,OrderRecipeComponent,
];
