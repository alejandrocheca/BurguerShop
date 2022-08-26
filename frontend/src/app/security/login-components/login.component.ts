import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../services/security.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  txtUsuario = 'admin@app.com';
  txtPassword = 'pass';

  constructor(public loginSrv: LoginService, private router: Router) { }

  logInOut() {
    if (this.loginSrv.isAutenticated) {
      this.loginSrv.logout();
    } else {
      this.loginSrv.login(this.txtUsuario, this.txtPassword).subscribe(
        data => {
          if (data) {
            this.router.navigateByUrl('');
          }
        },
      );
    }
  }
  registrar() {
    this.router.navigateByUrl('/registro');
  }

  ngOnInit(): void {}

}
