import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { environment } from 'src/environments/environment';

interface Response {
  token: string;
  userId: string;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(
    private http: HttpClient,
    private formBuilder: FormBuilder,
    private router: Router,
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  loginHandler(): void {
    const gatewayUrl = environment.gatewayUrl;

    this.http
      .post<Response>(`${gatewayUrl}/auth/token`, this.loginForm.value)
      .subscribe({
        next: (res) => {
          // set token in local storage and userId
          localStorage.setItem('token', res.token);
          localStorage.setItem('userId', res.userId);
          // Redirect to the desired route after successful login
          this.router.navigate(['/']);
        },
        error: (error) => {
          alert(error.error.message);
        },
      });
  }
}
