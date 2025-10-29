// import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
// import { ActivatedRoute, Router } from '@angular/router';
// import { CommonModule } from '@angular/common';
// import { HttpClient, HttpClientModule } from '@angular/common/http';
// import { Subscription } from 'rxjs';
// import { FormsModule } from '@angular/forms';
// import { Tickets } from '../../models/tickets.model';

// @Component({
//   selector: 'app-ticketscomponent',
//   standalone: true,
//   imports: [CommonModule, HttpClientModule, FormsModule],
//   templateUrl: './ticketscomponent.html',
//   styleUrls: ['./ticketscomponent.css'],
// })
// export class Ticketscomponent implements OnInit, OnDestroy {
//   userId = 0;
//   projectId = 0;
//   tickets: Tickets[] = [];
//   groupedTickets: { [key: string]: Tickets[] } = {};
//   isTester = false;
//   isAdmin = false;
//   developers: any[] = [];
//   selectedDevelopers: { [ticketId: number]: number } = {}; // For each ticket

//   private routeSub!: Subscription;
//   private baseUrl = 'http://localhost:8080/api';

//   constructor(
//     private activatedRoute: ActivatedRoute,
//     private router: Router,
//     private http: HttpClient,
//     private cdr: ChangeDetectorRef
//   ) {}

//   ngOnInit(): void {
//     this.routeSub = this.activatedRoute.paramMap.subscribe((params) => {
//       this.userId = Number(params.get('userId'));
//       this.projectId = Number(params.get('projectId'));
//       if (this.projectId) this.fetchTickets();
//       if (this.userId) this.checkUserRole();
//     });
//   }

//   // ✅ Check if user is Tester or Admin
//   checkUserRole(): void {
//     this.http.get<any[]>(`${this.baseUrl}/users/${this.userId}/roles`).subscribe({
//       next: (roles) => {
//         this.isTester = roles.some((r) => r.name?.toLowerCase() === 'tester');
//         this.isAdmin = roles.some((r) => r.name?.toLowerCase() === 'admin');

//         if (this.isAdmin) {
//           this.loadDevelopersForProject();

//         }
//       },
//       error: (err) => console.error('Error fetching roles:', err),
//     });
//   }

//   // ✅ Fetch all tickets for the project
//   fetchTickets(): void {
//     this.http
//       .get<Tickets[]>(`${this.baseUrl}/projects/${this.projectId}/tickets`)
//       .subscribe({
//         next: (data) => {
//           this.tickets = data;
//           this.groupTicketsByStatus();
//            this.cdr.detectChanges();
//         },
//         error: (err) => console.error('Error fetching tickets:', err),
//       });
//   }

//   // ✅ Group tickets by status (Open, In Progress, Closed, etc.)
//   groupTicketsByStatus(): void {
//     this.groupedTickets = this.tickets.reduce((acc, t) => {
//       const status = t.status?.toUpperCase() || 'UNKNOWN';
//       if (!acc[status]) acc[status] = [];
//       acc[status].push(t);
//       return acc;
//     }, {} as { [key: string]: Tickets[] });
//   }

//   // ✅ Load developers linked to this project
//   loadDevelopersForProject(): void {
//     this.http
//       .get<any[]>(`${this.baseUrl}/users/developers/${this.projectId}`)
//       .subscribe({
//         next: (data) => {
//           this.developers = data;
//           console.log('Developers for project:', data);
//            this.cdr.detectChanges();
//         },
//         error: (err) => console.error('Error fetching developers:', err),
//       });
//   }

//  // ✅ Assign a developer to a ticket (Admin only)
// assignTicket(ticketId: number): void {
//   // Find the ticket object by its ID
//   const ticket = this.tickets.find(t => t.id === ticketId);

//   if (!ticket || !ticket.assigneeId) {

//     alert('Please select a developer before assigning.');
//     return;
//   }

//   const request = { ticketId, assigneeId: ticket.assigneeId };

//   this.http.post(`${this.baseUrl}/tickets/assign`, request).subscribe({
//     next: (res) => {
//      // alert('✅ Ticket assigned successfully!');
//       this.fetchTickets(); // Refresh tickets after assignment
//        this.cdr.detectChanges();
//     },
//     error: (err) => {
//       console.error('Error assigning ticket:', err);
//       alert('❌ Error assigning ticket');
//     },
//   });
// }

// updateStatus(ticket: Tickets, newStatus: string): void {
//     const request = {
//       ticketId: ticket.id,
//       changedById: this.userId,
//       newStatus: newStatus,
//     };

//     this.http.post(`${this.baseUrl}/tickets/update-status`, request).subscribe({
//       next: (res) => {
        
//         this.fetchTickets(); // reload updated data
//          this.cdr.detectChanges();
//       },
//       error: (err) => console.error('❌ Error updating status:', err),
//     });

//   }


//   // ✅ View ticket details
//   viewTicket(ticketId: number): void {
//     this.router.navigate([
//       '/users',
//       this.userId,
//       'projects',
//       this.projectId,
//       'tickets',
//       ticketId,
//     ]);
//   }

//   // ✅ Navigate to Raise Ticket (Tester only)
//   raiseTicket(): void {
//     if (this.isTester) {
//       this.router.navigate([
//         '/users',
//         this.userId,
//         'projects',
//         this.projectId,
//         'raiseticket',
//       ]);
//     } else {
//       alert('Only testers can raise new tickets!');
//     }
//   }

//   ngOnDestroy(): void {
//     if (this.routeSub) this.routeSub.unsubscribe();
//   }
// }
import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { Tickets } from '../../models/tickets.model';

@Component({
  selector: 'app-ticketscomponent',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './ticketscomponent.html',
  styleUrls: ['./ticketscomponent.css'],
})
export class Ticketscomponent implements OnInit, OnDestroy {
  userId = 0;
  projectId = 0;
  tickets: Tickets[] = [];
  groupedTickets: { [key: string]: Tickets[] } = {};
  isTester = false;
  isAdmin = false;
  developers: any[] = [];
  selectedDevelopers: { [ticketId: number]: number } = {};

  private routeSub!: Subscription;
  private baseUrl = 'http://localhost:8080/api';

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // ✅ Get userId and projectId directly from route parameters
    this.routeSub = this.activatedRoute.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      this.projectId = Number(params.get('projectId'));

      if (this.projectId) this.fetchTickets();
      if (this.userId) this.checkUserRole();
    });
  }

  // ✅ Check if user is Tester or Admin
  checkUserRole(): void {
    this.http.get<any[]>(`${this.baseUrl}/users/${this.userId}/roles`).subscribe({
      next: (roles) => {
        this.isTester = roles.some((r) => r.name?.toLowerCase() === 'tester');
        this.isAdmin = roles.some((r) => r.name?.toLowerCase() === 'admin');

        if (this.isAdmin) this.loadDevelopersForProject();
      },
      error: (err) => console.error('Error fetching roles:', err),
    });
  }

  // ✅ Fetch all tickets for the project
  fetchTickets(): void {
    this.http
      .get<Tickets[]>(`${this.baseUrl}/projects/${this.projectId}/tickets`)
      .subscribe({
        next: (data) => {
          this.tickets = data;
          this.groupTicketsByStatus();
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Error fetching tickets:', err),
      });
  }

  // ✅ Group tickets by status
  groupTicketsByStatus(): void {
    this.groupedTickets = this.tickets.reduce((acc, t) => {
      const status = t.status?.toUpperCase() || 'UNKNOWN';
      if (!acc[status]) acc[status] = [];
      acc[status].push(t);
      return acc;
    }, {} as { [key: string]: Tickets[] });
  }

  // ✅ Load developers linked to this project
  loadDevelopersForProject(): void {
    this.http
      .get<any[]>(`${this.baseUrl}/users/developers/${this.projectId}`)
      .subscribe({
        next: (data) => {
          this.developers = data;
          console.log('Developers for project:', data);
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Error fetching developers:', err),
      });
  }

  // ✅ Assign a developer to a ticket (Admin only)
  assignTicket(ticketId: number): void {
    const ticket = this.tickets.find((t) => t.id === ticketId);
    if (!ticket || !ticket.assigneeId) {
      alert('Please select a developer before assigning.');
      return;
    }

    const request = { ticketId, assigneeId: ticket.assigneeId };

    this.http.post(`${this.baseUrl}/tickets/assign`, request).subscribe({
      next: () => {
        this.fetchTickets();
        this.cdr.detectChanges();
        this.updateStatus(ticket, 'ASSIGNED');
      },
      error: (err) => {
        console.error('Error assigning ticket:', err);
        alert('❌ Error assigning ticket');
      },
    });
  }

  // ✅ Update ticket status
updateStatus(ticket: any, newStatus: string): void {
  if (!newStatus) return;

  const request = {
    ticketId: ticket.id,
    changedById: this.userId, // fetched from route param
    newStatus: newStatus
  };

  this.http.post(`${this.baseUrl}/tickets/update-status`, request).subscribe({
    next: (res) => {
     
      ticket.status = newStatus;

       this.cdr.detectChanges();
      this.fetchTickets(); // reload updated data
       this.cdr.detectChanges();
      
    },
    error: (err) => {
      console.error('Error updating status:', err);
    },
  });
}
showMyTicketsOnly: boolean = false;


getMyTicketsOnly(): void {
  this.showMyTicketsOnly = !this.showMyTicketsOnly; // toggle

  const userId = this.userId; // from param or login

  if (this.showMyTicketsOnly) {
    // ✅ Filter tickets where user is reporter or assignee
    const myTickets = this.tickets.filter(
      (t) => t.reporterId === userId || t.assigneeId === userId
    );

    // ✅ Group filtered tickets by status
    this.groupedTickets = myTickets.reduce((acc, ticket) => {
      const status = ticket.status?.toUpperCase().trim() || 'UNKNOWN';
      if (!acc[status]) acc[status] = [];
      acc[status].push(ticket);
      return acc;
    }, {} as { [key: string]: Tickets[] });

    console.log('✅ Showing My Tickets:', this.groupedTickets);
  } else {
    // ✅ Show all tickets again
    this.groupTicketsByStatus();
    console.log('📋 Showing All Tickets:', this.groupedTickets);
  }
}





  // ✅ View ticket details
  viewTicket(ticketId: number): void {
    this.router.navigate([
      '/users',
      this.userId,
      'projects',
      this.projectId,
      'tickets',
      ticketId,
    ]);
  }

  // ✅ Navigate to Raise Ticket (Tester only)
  raiseTicket(): void {
    if (this.isTester) {
      this.router.navigate([
        '/users',
        this.userId,
        'projects',
        this.projectId,
        'raiseticket',
      ]);
    } else {
      alert('Only testers can raise new tickets!');
    }
  }

  ngOnDestroy(): void {
    if (this.routeSub) this.routeSub.unsubscribe();
  }
}
