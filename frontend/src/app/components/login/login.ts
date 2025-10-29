import { ChangeDetectorRef, Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

import { Auth } from '../../services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class Login {
  loginForm: FormGroup;
  submitted = false;
  private baseUrl = 'http://localhost:8080/api/auth/login';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private auth: Auth // ✅ inject service
  ) {
    this.loginForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  onSubmit(): void {
    this.submitted = true;
    if (this.loginForm.invalid) return;

    const { username, password } = this.loginForm.value;
    const loginRequest = { usernameOrEmail: username, password };

    this.http.post(this.baseUrl, loginRequest).subscribe({
      next: (res: any) => {
        console.log('✅ Login successful:', res);
        this.auth.login(res.token); // ✅ Update auth state

        const userId = res.user?.id;
        if (userId) {
          this.router.navigate([`/users/${userId}/projects`]);
        } else {
          alert('User ID not found in response!');
        }
      },
      error: (err) => {
        console.error('❌ Login failed:', err);
        alert('Invalid username or password!');
      },
    });
  }

  goToRegister(): void {
    this.router.navigate(['/register']);
  }
}
