import { Component, OnInit } from '@angular/core';
import { LoginComponent } from 'src/app/security/login-components/login.component';
import { LoginService } from 'src/app/security/services/security.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})

export class MenuComponent implements OnInit {
  constructor(public loginSrv: LoginService, public logOut: LoginComponent) { }
  ngOnInit(): void {
  }
}
