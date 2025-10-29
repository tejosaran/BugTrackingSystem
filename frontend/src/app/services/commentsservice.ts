import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comments } from '../models/comments.model';

@Injectable({
  providedIn: 'root',
})
export class Commentsservice {
  private baseUrl = 'http://localhost:8080/api/comments'; // adjust this to match your backend route

  constructor(private http: HttpClient) {}

  // 🟩 Get all comments for a specific ticket
  getCommentsByTicket(ticketId: number): Observable<Comments[]> {
    return this.http.get<Comments[]>(`${this.baseUrl}/ticket/${ticketId}`);
  } 

  // 🟦 Get a single comment by ID
  getCommentById(id: number): Observable<Comments> {
    return this.http.get<Comments>(`${this.baseUrl}/${id}`);
  }

  // 🟨 Add a new comment
  addComment(comment: Comments): Observable<Comments> {
    return this.http.post<Comments>(this.baseUrl, comment);
  }

  // 🟧 Update an existing comment
  updateComment(id: number, comment: Comments): Observable<Comments> {
    return this.http.put<Comments>(`${this.baseUrl}/${id}`, comment);
  }

  // 🟥 Delete a comment
  deleteComment(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}
