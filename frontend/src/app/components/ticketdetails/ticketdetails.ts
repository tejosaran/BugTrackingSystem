import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TicketsService } from '../../services/ticketsservice';
import { Commentsservice } from '../../services/commentsservice';
import { Tickets } from '../../models/tickets.model';
import { Comments } from '../../models/comments.model';

@Component({
  selector: 'app-ticketdetails',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './ticketdetails.html',
  styleUrls: ['./ticketdetails.css'],
})
export class Ticketdetails implements OnInit {
   userId!: number;   
  ticketId!: number;
  ticket!: Tickets;
  comments: Comments[] = [];
  newComment: string = '';
  loadingTicket: boolean = false;
  loadingComments: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private ticketsService: TicketsService,
    private commentsService: Commentsservice,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('userId'));
    this.ticketId = Number(this.route.snapshot.paramMap.get('ticketId'));
    
    if (this.ticketId) {
      this.loadTicketDetails();
    } else {
      console.error('Invalid ticket ID in route.');
    }
  }

  // 🟩 Load ticket + comments
  loadTicketDetails(): void {
    this.loadingTicket = true;
    this.ticketsService.getTicketById(this.ticketId).subscribe({
      next: (ticketData) => {
        this.ticket = ticketData;
        this.cdr.detectChanges();
        console.log('✅ Ticket details loaded:', this.ticket);
         this.cdr.detectChanges();
        this.loadingTicket = false;
        this.loadComments();
      },
      error: (err) => {
        console.error('❌ Error fetching ticket details:', err);
        this.loadingTicket = false;
      },
    });
  }

  // 🟨 Load all comments for this ticket
  loadComments(): void {
    this.loadingComments = true;
    this.commentsService.getCommentsByTicket(this.ticketId).subscribe({
      next: (data) => {
        this.comments = data.sort((a, b) =>
          a.createdAt && b.createdAt ? new Date(a.createdAt).getTime() - new Date(b.createdAt).getTime() : 0
        );
        console.log('💬 Comments loaded:', this.comments);
         this.cdr.detectChanges();
        this.loadingComments = false;
      },
      error: (err) => {
        console.error('❌ Error fetching comments:', err);
        this.loadingComments = false;
      },
    });
  }

  
  // 🟦 Add a new comment
  addComment(): void {
    const trimmedComment = this.newComment.trim();
    if (!trimmedComment) return;
    

    const commentData: Comments = {
      content: trimmedComment,
      ticketId: this.ticketId,
       authorId: this.userId,  // 🔹 Replace with logged-in user ID from token
      createdAt: new Date().toISOString(),
    };

    this.commentsService.addComment(commentData).subscribe({
      next: (savedComment) => {
        console.log('🟢 Comment added:', savedComment);

        this.comments.push(savedComment);
        this.newComment = '';
        this.loadTicketDetails();
         this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('❌ Error adding comment:', err);
      },
    });
  }
}
