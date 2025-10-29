import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Projects } from '../models/projects.model';

@Injectable({
  providedIn: 'root'
})
export class Projectservice {
   private baseUrl = 'http://localhost:8080/api/projects'; // backend base path

  constructor(private http: HttpClient) {}

  // ✅ GET all projects for a specific user
  getProjectsByUser(userId: number): Observable<Projects[]> {
    return this.http.get<Projects[]>(`${this.baseUrl}/user/${userId}`);
  }
}
