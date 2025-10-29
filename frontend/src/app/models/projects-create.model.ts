export interface ProjectsCreate {
  name: string;
  description?: string;
  status?: string; // default 'ACTIVE'
  createdBy: number;
}
