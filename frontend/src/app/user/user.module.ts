import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserEditComponent, UserListComponent, UsersAddComponent, UserViewComponent, USER_COMPONENTS } from './components/user.component';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

const routes: Routes = [
  { path: '', component: UserListComponent},
  { path: 'add', component: UsersAddComponent},
  { path: ':id/edit', component: UserEditComponent},
  { path: ':id', component: UserViewComponent},
];

@NgModule({
  declarations: [USER_COMPONENTS],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule.forChild(routes),
  ],
  exports:[USER_COMPONENTS]
})
export class UserModule { }
