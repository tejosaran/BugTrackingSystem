import { Users } from "./users.model";

export interface UserLoginResponse {
  token: string;
  message: string;
  user: Users;
}