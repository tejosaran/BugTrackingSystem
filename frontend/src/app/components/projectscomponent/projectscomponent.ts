import {
  Component,
  OnInit,
  OnDestroy,
  ChangeDetectorRef,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Projects } from '../../models/projects.model';

@Component({
  selector: 'app-projectscomponent',
  standalone: true,
  imports: [CommonModule, HttpClientModule, FormsModule],
  templateUrl: './projectscomponent.html',
  styleUrls: ['./projectscomponent.css'],
})
export class Projectscomponent implements OnInit, OnDestroy {
  userId = 0;
  projects: Projects[] = [];
  isAdmin = false;
  availableUsers: { [key: number]: any[] } = {};
  selectedUser: { [key: number]: number } = {};
  private routeSub!: Subscription;

  // ✅ For project creation
  showAddProject = false;
  newProject = { name: '', description: '' };

  constructor(
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // ✅ Get userId from route
    this.routeSub = this.activatedRoute.paramMap.subscribe((params) => {
      this.userId = Number(params.get('userId'));
      if (this.userId) {
        this.checkIfAdmin();
      }
    });
  }

  // ✅ Check whether user is admin
  checkIfAdmin(): void {
    this.http
      .get<any[]>(`http://localhost:8080/api/users/${this.userId}/roles`)
      .subscribe({
        next: (roles) => {
          console.log('User roles:', roles);
          this.isAdmin = roles.some((r) => r.name?.toLowerCase() === 'admin');

          if (this.isAdmin) this.loadAllProjects();
          else this.loadUserProjects();
        },
        error: (err) => console.error('Error fetching roles:', err),
      });
  }

  // ✅ Load all projects (Admin)
  loadAllProjects(): void {
    this.http.get<Projects[]>(`http://localhost:8080/api/projects`).subscribe({
      next: (data) => {
        this.projects = data;
        console.log('All projects loaded:', data);
        this.projects.forEach((p) => this.loadAvailableUsersForProject(p.id!));
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error loading all projects:', err),
    });
  }

  // ✅ Load projects for current user (Non-admin)
  loadUserProjects(): void {
    this.http
      .get<Projects[]>(`http://localhost:8080/api/projects/user/${this.userId}`)
      .subscribe({
        next: (data) => {
          this.projects = data;
          console.log('Projects loaded for user:', data);
          this.cdr.detectChanges();
        },
        error: (err) => console.error('Error loading user projects:', err),
      });
  }

  // ✅ Load users NOT in a project
  loadAvailableUsersForProject(projectId: number): void {
    this.http
      .get<any[]>(`http://localhost:8080/api/users/not-in-project/${projectId}`)
      .subscribe({
        next: (users) => {
          this.availableUsers[projectId] = users;
          console.log(`Users not in project ${projectId}:`, users);
          this.cdr.detectChanges();
        },
        error: (err) =>
          console.error('Error fetching users not in project:', err),
      });
  }

  // ✅ Get dropdown list of available users
  getAvailableUsersForProject(p: any): any[] {
    return this.availableUsers[p.id] || [];
  }

  // ✅ Add member to project
  addMemberToProject(projectId: number): void {
    const userId = this.selectedUser[projectId];
    if (!userId) {
      alert('Please select a user before adding.');
      return;
    }

    const request = { projectId, userId };

    this.http
      .post(`http://localhost:8080/api/project-members/add`, request)
      .subscribe({
        next: () => {
          alert('✅ User added successfully!');
          this.loadAvailableUsersForProject(projectId); // Refresh dropdown
        },
        error: (err) => {
          console.error('Error adding user:', err);
          alert('❌ Failed to add user');
        },
      });
  }

  // ✅ Helper to show user roles in dropdown
  getUserRoles(user: any): string {
    return user.roles?.map((r: any) => r.name).join(', ') || 'No Role';
  }

  // ✅ Navigate to tickets
  goToTickets(projectId: number): void {
    this.router.navigate([
      '/users',
      this.userId,
      'projects',
      projectId,
      'tickets',
    ]);
  }

  // ✅ Toggle add project form visibility (Admin)
  toggleAddProject(): void {
    this.showAddProject = !this.showAddProject;
  }

  // ✅ Create new project (Admin only)
  createProject(): void {
    if (!this.newProject.name.trim() || !this.newProject.description.trim()) {
      alert('Please fill all fields.');
      return;
    }

    const payload = {
      name: this.newProject.name,
      description: this.newProject.description,
      createdById: this.userId, // link to admin creating project
    };

    this.http.post(`http://localhost:8080/api/projects`, payload).subscribe({
      next: () => {
        
        this.newProject = { name: '', description: '' };
        this.showAddProject = false;
        this.loadAllProjects(); // refresh list
      },
      error: (err) => {
        console.error('Error creating project:', err);
        alert('❌ Failed to create project.');
      },
    });
  }

  ngOnDestroy(): void {
    if (this.routeSub) this.routeSub.unsubscribe();
  }
}
