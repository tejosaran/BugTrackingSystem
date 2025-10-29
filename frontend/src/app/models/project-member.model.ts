import { Users } from './users.model';
import { Projects } from './projects.model';

export interface ProjectMembers {
  id?: number;

  projectId?: number;
  project?: Projects;

  userId?: number;
  user?: Users;

  joinedAt?: string; // LocalDateTime → string (ISO format)
}
