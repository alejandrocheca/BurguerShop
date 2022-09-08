
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { UserViewModelService } from '../services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './tmpl-main.component.html',
  styleUrls: ['./component.component.css']
})
export class UserComponent implements OnInit, OnDestroy {
  constructor(protected vm: UserViewModelService) { }
  public get VM(): UserViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
  ngOnDestroy():void{
    this.vm.clear();
  }
}
@Component({
  selector: 'app-user-list',
  templateUrl: './tmpl-list.component.html',
  styleUrls: ['./component.component.css']
})
export class UserListComponent implements OnInit {
  constructor(protected vm: UserViewModelService) { }
  public get VM(): UserViewModelService{ return this.vm; }
  ngOnInit(): void {
    this.vm.list();
  }
}

@Component({
  selector: 'app-user-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./component.component.css']
})
export class UserViewComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: UserViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): UserViewModelService { return this.vm; }
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
  selector: 'app-user-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class UserEditComponent implements OnInit, OnDestroy {
  private obs$: any;
  constructor(protected vm: UserViewModelService,
    protected route: ActivatedRoute, protected router: Router) { }
  public get VM(): UserViewModelService { return this.vm; }
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
  selector: 'app-user-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./component.component.css']
})
export class UsersAddComponent implements OnInit {
  constructor(protected vm: UserViewModelService) { }
  public get VM(): UserViewModelService { return this.vm; }
  ngOnInit(): void {
    this.VM.add();
  }
}
export const USER_COMPONENTS = [
  UserComponent, UserListComponent,UserViewComponent,
  UserEditComponent, UsersAddComponent,
];
