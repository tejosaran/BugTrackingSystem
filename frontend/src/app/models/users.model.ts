import { Roles } from "./roles.model";

export interface Users {
  id: number;
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  isActive: boolean;
  createdAt: string; // Use string for date-time from backend (ISO format)
  updatedAt: string;
  roles: Roles[];
}