import { Comments } from './comments.model';
import { Projects } from './projects.model';
import { Users } from './users.model';


export interface Tickets {
  id?: number;
  title?: string;
  description?: string;
  status?: string;

  projectId?: number;
  project?: Projects;

  reporterId?: number;
  reporter?: Users;
  reporterName?: string;

  assigneeId?: number;
  assignee?: Users;
  assigneeName?: string;

  createdAt?: string; // LocalDateTime → string (ISO format)
  updatedAt?: string;

  comments?: Comments[];
  commentCount?: number;
  attachmentCount?: number;
}
