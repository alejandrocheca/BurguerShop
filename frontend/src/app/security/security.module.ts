
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login-components/login.component';
import { RegisterUserComponent } from './register-components/register.component';

const routes: Routes = [
  { path: 'register', component: RegisterUserComponent},
  { path: 'login', component: LoginComponent},
];

@NgModule({
  declarations: [
    LoginComponent, RegisterUserComponent
  ],
  imports: [
    CommonModule, FormsModule,
    RouterModule.forChild(routes),
  ],
  exports:[LoginComponent]
})
export class SecurityModule { }
