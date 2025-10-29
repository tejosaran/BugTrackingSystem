package com.tejosaran.bugtracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejosaran.bugtracking.dto.ProjectsCreateDTO;
import com.tejosaran.bugtracking.dto.ProjectsDTO;
import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.service.ProjectsService;

@CrossOrigin
@RestController
@RequestMapping("/api/projects")

public class ProjectsController {

    @Autowired
    private ProjectsService projectsService;

    @GetMapping
    public ResponseEntity<List<ProjectsDTO>> getAllProjects() {
        return ResponseEntity.ok(projectsService.getAllProjects());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectsDTO> getProject(@PathVariable Long id) {
        ProjectsDTO dto = projectsService.getProjectById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProjectsDTO> addProject(@RequestBody ProjectsCreateDTO dto) {
        // Replace this with actual logged-in user in your app
        UsersEntity dummyUser = new UsersEntity();
        dummyUser.setId(1L); // just for example
        ProjectsDTO saved = projectsService.addProject(dto, dummyUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectsService.deleteProject(id);
        return ResponseEntity.ok("Project deleted successfully");
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectsDTO>> getProjectsByUser(@PathVariable Long userId) {
        List<ProjectsDTO> projects = projectsService.getProjectsByUser(userId);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}/tickets")
    public ResponseEntity<List<TicketsDTO>> getTicketsByProject(@PathVariable Long projectId) {
        List<TicketsDTO> tickets = projectsService.getTicketsByProject(projectId);
        return ResponseEntity.ok(tickets);
    }

}
