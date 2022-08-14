import { HttpClient, HttpContext, HttpContextToken } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { RESTDAOService } from 'src/app/common/services/RestService';
import { LoggerService } from 'src/lib/my-core/services/logger.service';
import { NavigationService } from 'src/app/common/services/navigation.service';
import { ModoCRUD } from 'src/app/common/services/tipos';
import { AuthService } from 'src/app/security/services/security.service';
const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

@Injectable({

  providedIn: 'root'
})
export class IngredientsDAOService extends RESTDAOService<any, any> {
  constructor(http: HttpClient) {
    super(http, 'ingredients',{context: {AUTH_REQUIRED:true}});
  }
}

@Injectable({
  providedIn: 'root'
})
export class IngredientViewModelService {
  protected modo: ModoCRUD = 'list';
  protected listado: Array<any> = [];
  protected elemento: any = {};
  protected idOriginal: any = null;
  protected listURL = '/ingredients';

  constructor(
    protected out: LoggerService,
    protected dao: IngredientsDAOService,
    protected router: Router,
    private navigation: NavigationService,
    public auth: AuthService,
    ) { }

  public get Modo(): ModoCRUD { return this.modo; }
  public get Listado(): Array<any> { return this.listado; }
  public get Elemento(): any { return this.elemento; }
  public get isAutenticated(): boolean { return this.auth.isAutenticated; }

  public list(): void {
    this.dao.query().subscribe({
      next: data => {
        this.listado = data;
        this.modo = 'list';
      },
      error: err => this.out.error(err.message)
    });
  }
  public add(): void {
    this.elemento = {};
    this.modo = 'add';
  }
  public edit(key: any): void {
    this.dao.get(key).subscribe({
      next: data => {
        this.elemento = data;
        this.idOriginal = key;
        this.modo = 'edit';
      },
      error: err => this.out.error(err.message)
    });
  }
  public view(key: any): void {
    this.dao.get(key).subscribe({
      next: data => {
        this.elemento = data;
        this.modo = 'view';
      },
      error: err => this.out.error(err.message)
    });
  }
  public delete(key: any): void {
    if (!window.confirm('¿Seguro?')) { return; }
    this.dao.remove(key).subscribe({
      next: data =>this.list(),
      error: err =>this.out.error(err.message)
    });
  }
  clear() {
    this.elemento = {};
    this.idOriginal = null;
    this.listado = [];
  }
  public cancel(): void {
    this.elemento = {};
    this.idOriginal = null;
    this.navigation.back();
  }
  public send(): void {
    switch (this.modo) {
      case 'add':
        this.dao.add(this.elemento).subscribe({
          next: data => this.cancel(),
          error: err => this.out.error(err.message)
        });
        break;
      case 'edit':
        this.dao.change(this.idOriginal, this.elemento).subscribe({
          next: data => this.cancel(),
          error: err => this.out.error(err.message)
        });
        break;
      case 'view':
        this.cancel();
        break;
    }
  }
}
