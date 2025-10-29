import { Users } from './users.model';
import { Tickets } from './tickets.model';

export interface Projects {
  id?: number;
  name?: string;
  description?: string;
  status?: string;

  createdById?: number;
  createdBy?: Users;

  createdAt?: string; // LocalDateTime → string (ISO format)
  updatedAt?: string;

  tickets?: Tickets[];
}
