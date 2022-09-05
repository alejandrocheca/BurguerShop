import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { HttpClient, HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpContextToken } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

export const AUTH_REQUIRED = new HttpContextToken<boolean>(() => false);

@Injectable({providedIn: 'root'})
export class AuthService {
  private isAuth = false;
  private accessToken: string = '';
  private refreshToken: String = '';

  constructor() {
    if (localStorage && localStorage['AuthService']) {
      const rslt = JSON.parse(localStorage['AuthService']);
      this.isAuth = rslt.isAuth;
      this.accessToken = rslt.accessToken;
      this.refreshToken = rslt.refreshToken;
    }
  }
  get AuthorizationHeader() { return "Bearer "+this.accessToken;  }
  get isAutenticated() { return this.isAuth; }
  get RefreshToken() { return this.refreshToken; }

  login(accessToken: string, refreshToken: string) {
    this.isAuth = true;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    if (localStorage) {
      localStorage['AuthService'] = JSON.stringify({ isAuth: this.isAuth, accessToken, refreshToken });
    }
  }
  logout() {
    this.isAuth = false;
    this.accessToken = '';
    this.refreshToken = '';
    if (localStorage) {
      localStorage.removeItem('AuthService');
    }
  }
}
class LoginResponse {
  refreshToken: string = '';
  accessToken: string ='';
  expiration: number = 0;
}
@Injectable({providedIn: 'root'})
export class LoginService {
  constructor(private http: HttpClient, private auth: AuthService) { }
  get isAutenticated() { return this.auth.isAutenticated;  }
  get refreshToken() { return this.auth.RefreshToken;  }

  login(usr: string, pwd: string) {
    return new Observable(observable =>
      this.http.post<LoginResponse>(environment.apiURL + 'users/login', { email: usr, password: pwd })
        .subscribe({
          next: data => {
            if (data.accessToken != null) {
              this.auth.login(data.accessToken ?? '', data.refreshToken ?? '');
            }
            observable.next(this.auth.isAutenticated);
          },
          error: err => observable.error(err)
       })
    );
  }
  logout() {
    this.auth.logout();
  }
}

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private auth: AuthService) { }
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!this.auth.isAutenticated) {
      return next.handle(req);
    }
    const authReq = req.clone(
      { headers: req.headers.set('Authorization', this.auth.AuthorizationHeader) }
    );

    return next.handle(authReq);
  }
}
@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.authService.isAutenticated;
  }
}
export class Role {
  role: string = '';
}
export class User {
  id: string = '';
  password: string = '';
  email: string = '';
  surname:string = '';
  name: string = '';
  role: Role = {role:''};
}
export class CreateUserDTO {
  password: string = '';
  email: string = '';
  surname:string = '';
  name: string = '';
}
export class UpdateUserDTO {
  password: string = '';
  email: string = '';
  surname:string = '';
  name: string = '';
  role: Role = {role:''};
}
@Injectable({providedIn: 'root'})
export class RegisterUserDAO  {
  private baseUrl = environment.apiURL + 'users/register';
  private options = { withCredentials: true };

  constructor(private http: HttpClient) { }
  add(item: CreateUserDTO)  {
    return this.http.post(this.baseUrl, item);
  }
  get() {
    return this.http.get<User>(this.baseUrl, this.options);
  }
  change(item: UpdateUserDTO) {
    return this.http.put(this.baseUrl, item, this.options);
  }
}
