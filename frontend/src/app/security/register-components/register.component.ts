import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormArray, ValidatorFn, AbstractControl, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { LoggerService } from 'src/lib/my-core';
import { CreateUserDTO, LoginService, RegisterUserDAO, User } from '../services/security.service';

@Component({
  selector: 'app-register-user',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterUserComponent implements OnInit {
  public name: string = '';
  public surname: string = '';
  public email: string = '';
  public passwordValue: string = '';
  public passwordConfirm: string = '';

  private model: CreateUserDTO = new CreateUserDTO();

  constructor(private dao: RegisterUserDAO,
    private out: LoggerService, private router: Router, private login: LoginService) { }

  passwordMatchValidator(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => control?.get('passwordValue')?.value === control?.get('passwordConfirm')?.value
      ? null : { 'mismatch': 'Son distintos' };
  }
  ngOnInit() {
  }

  send(): void {
    this.model = ({
      password: this.passwordValue,
      name: this.name,
      surname: this.surname,
      email: this.email,
    } as CreateUserDTO);
    this.dao.add(this.model).subscribe(
      rslt => {
        this.login.login(this.model.email, this.model.password).subscribe(
          datos => {
            if (datos) {
              this.router.navigateByUrl('/');
            }
          },
        );
      },
    );
  }
}
