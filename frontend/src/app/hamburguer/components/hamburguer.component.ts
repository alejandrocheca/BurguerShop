import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { HamburguerViewModelService } from '../services/hamburguer.service';

@Component({
  selector: 'app-hamburguer',
  templateUrl: './tmpl-main.component.html',
  styleUrls: ['./component.component.css']
})
export class HamburguerComponent implements OnInit, OnDestroy {
  constructor(protected vm: HamburguerViewModelService) { }
  public get VM(): HamburguerViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy():void{
    this.vm.clear();
  }
}
@Component({
  selector: 'app-hamburguer-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./component.component.css']
})
export class HamburguerListComponent implements OnInit {
  constructor(protected vm: HamburguerViewModelService) { }
  public get VM(): HamburguerViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
}

@Component({
  selector: 'app-hamburguer-list-admin',
  templateUrl: './tmpl-list-admin.component.html',
  styleUrls: ['./component.component.css']
})
export class HamburguerListAdminComponent implements OnInit {
  constructor(protected vm: HamburguerViewModelService) { }
  public get VM(): HamburguerViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
}
@Component({
  selector: 'app-hamburguer-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./component.component.css']
})
export class HamburguerViewComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: HamburguerViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): HamburguerViewModelService { return this.vm; }
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
  selector: 'app-hamburguer-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class HamburguerEditComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: HamburguerViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): HamburguerViewModelService { return this.vm; }
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
  selector: 'app-hamburguer-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class HamburguersAddComponent implements OnInit {
  constructor(protected vm: HamburguerViewModelService) { }
  public get VM(): HamburguerViewModelService { return this.vm; }
  ngOnInit(): void {
    this.VM.add();
  }
}
export const HAMBURGUER_COMPONENTS = [
  HamburguerComponent, HamburguerListComponent,HamburguerViewComponent,
  HamburguerEditComponent, HamburguersAddComponent,HamburguerListAdminComponent
];
