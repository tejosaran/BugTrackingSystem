import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { Commentsservice } from '../../services/commentsservice'; 
import { Comments } from '../../models/comments.model'; 

@Component({
  selector: 'app-commentscomponent',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './commentscomponent.html',
  styleUrls: ['./commentscomponent.css'],
})
export class Commentscomponent implements OnInit {
  userId: number = 0;
  projectId: number = 0;
  ticketId: number = 0;
  comments: Comments[] = [];
  newComment: string = '';

  constructor(private route: ActivatedRoute, private commentsService: Commentsservice,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('userId'));
    this.projectId = Number(this.route.snapshot.paramMap.get('projectId'));
    this.ticketId = Number(this.route.snapshot.paramMap.get('ticketId'));

    console.log('🟩 Params:', {
      userId: this.userId,
      projectId: this.projectId,
      ticketId: this.ticketId,
    });

    if (this.ticketId) {
      this.loadComments();
    }
  }

  // 🟦 Load all comments for a ticket
  loadComments(): void {
    this.commentsService.getCommentsByTicket(this.ticketId).subscribe({
      next: (data) => {
        this.comments = data;
        console.log('🟩 Comments fetched successfully:', this.comments);
         this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('❌ Error fetching comments:', err);
      },
    });
  }

  // 🟨 Add a new comment
  addComment(): void {
    if (!this.newComment.trim()) {
      console.warn('⚠️ Empty comment not added');
      return;
    }

    const newCommentObj: Comments = {
      content: this.newComment,
      ticketId: this.ticketId,
      authorId: this.userId,
    };

    this.commentsService.addComment(newCommentObj).subscribe({
      next: (c) => {
        console.log('🟢 Comment added:', c);
        this.comments.push(c);
        this.newComment = '';
        console.log('🟣 Updated comments list:', this.comments);
         this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('❌ Error adding comment:', err);
      },
    });
  }
}
