import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tickets } from '../models/tickets.model';

@Injectable({
  providedIn: 'root'
})
export class TicketsService {
  private baseUrl = 'http://localhost:8080/api/tickets'; // ✅ change if your backend is different

  constructor(private http: HttpClient) {}

  // ✅ Get tickets by projectId
  getTicketsByProject(projectId: number): Observable<Tickets[]> {
    return this.http.get<Tickets[]>(`${this.baseUrl}/project/${projectId}`);
  }

   getTicketById(ticketId: number): Observable<Tickets> {
    return this.http.get<Tickets>(`${this.baseUrl}/${ticketId}`);
  }

  // ✅ Add new ticket
  addTicket(ticket: Tickets): Observable<Tickets> {
    return this.http.post<Tickets>(this.baseUrl, ticket);
  }

  

  // ✅ Delete ticket
  deleteTicket(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }
}