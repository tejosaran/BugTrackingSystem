import { Users } from './users.model';
import { Tickets } from './tickets.model';

export interface Comments {
  id?: number;
  content?: string;

  ticketId?: number;
  ticket?: Tickets;

  authorId?: number;
  author?: Users;

  createdAt?: string; // LocalDateTime → string (ISO format)
  updatedAt?: string;
}
