import { Component } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  templateUrl: './register.html',
  styleUrls: ['./register.css'],
  imports: [CommonModule,FormsModule,ReactiveFormsModule],
})
export class Register {
  registerForm!: FormGroup;
  submitted = false;
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      roleIds: [[]] // empty array for multiple selection
    });
  }

  onRoleChange(event: any): void {
    const roles = this.registerForm.get('roleIds')?.value as number[];
    const value = Number(event.target.value);

    if (event.target.checked) {
      roles.push(value);
    } else {
      const index = roles.indexOf(value);
      if (index !== -1) roles.splice(index, 1);
    }

    this.registerForm.patchValue({ roleIds: roles });
  }

  onSubmit(): void {
    this.submitted = true;

    if (this.registerForm.invalid || this.registerForm.value.roleIds.length === 0) {
      return;
    }

    this.http.post(`${this.baseUrl}/register`, this.registerForm.value).subscribe({
      next: () => {
       
         this.goToLogin();
      },
      error: (err) => {
        console.error('Registration failed:', err);
        alert('❌ Registration failed! Please try again.');
      }
    });
  }

  goToLogin(): void {
    this.router.navigate(['/']);
  }
}