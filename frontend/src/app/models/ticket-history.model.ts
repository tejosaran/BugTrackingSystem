import { Tickets } from './tickets.model';
import { Users } from './users.model';

export interface TicketHistory {
  id?: number;
  ticketId?: number;
  ticket?: Tickets;

  oldValue?: string;
  newValue?: string;

  changedById?: number;
  changedBy?: Users;
  changedByName?: string;

  changeDate?: string; // LocalDateTime → string (ISO format)
}
