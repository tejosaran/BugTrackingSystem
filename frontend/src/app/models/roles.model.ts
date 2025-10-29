import { Users } from './users.model';

export interface Roles {
  id?: number;
  name?: string;
  users?: Users[];
}
