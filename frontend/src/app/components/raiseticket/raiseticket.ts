import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TicketsService } from '../../services/ticketsservice';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-raiseticket',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './raiseticket.html',
  styleUrls: ['./raiseticket.css'],
})
export class Raiseticket implements OnInit {
  ticketForm!: FormGroup;
  userId!: number;
  projectId!: number;
  isSubmitting = false;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private ticketsService: TicketsService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // ✅ Get userId and projectId from route
    this.route.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      this.projectId = Number(params.get('projectId'));
    });

    // ✅ Initialize the form
    this.ticketForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(3)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      priority: ['MEDIUM', Validators.required], // Default priority
    });
  }

  onSubmit(): void {
    if (this.ticketForm.invalid) {
      alert('❌ Please fill in all required fields correctly.');
      return;
    }

    this.isSubmitting = true;

    const ticketData = {
      ...this.ticketForm.value,
      reporterId: this.userId,   // 👈 from route param
      projectId: this.projectId, // 👈 from route param
    };

    console.log('Submitting Ticket:', ticketData);

    this.ticketsService.addTicket(ticketData).subscribe({
      next: () => {
        console.log('Ticket created successfully');
        this.isSubmitting = false;
        this.router.navigate([
          '/users',
          this.userId,
          'projects',
          this.projectId,
          'tickets',
        ]);
      },
      error: (err) => {
        console.error('Error creating ticket:', err);
        this.isSubmitting = false;
        alert('❌ Failed to raise ticket.');
      },
    });
  }

  cancel(): void {
    this.router.navigate([
      '/users',
      this.userId,
      'projects',
      this.projectId,
      'tickets',
    ]);
  }
}
