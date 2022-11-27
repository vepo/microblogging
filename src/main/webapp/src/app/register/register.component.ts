import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less']
})
export class RegisterComponent implements OnInit {

  registerForm = new FormGroup({
    handle: new FormControl("", [Validators.required, Validators.minLength(4), Validators.maxLength(20)]),
    email: new FormControl("", [Validators.required, Validators.email]),
    password: new FormControl("", [Validators.required, Validators.minLength(4)])
  });

  isSuccessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
  }

  get handle() {
    return this.registerForm.get('handle');
  }

  get email() {
    return this.registerForm.get('email');
  }

  get password() {
    return this.registerForm.get('password');
  }

  onSubmit(): void {

    this.authService.register({
      handle: this.handle?.value!,
      email: this.email?.value!,
      password: this.password?.value!
    }).subscribe({
      next:
        data => {
          this.isSuccessful = true;
          this.isSignUpFailed = false;
        },
      error: err => {
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    }
    );
  }
}
