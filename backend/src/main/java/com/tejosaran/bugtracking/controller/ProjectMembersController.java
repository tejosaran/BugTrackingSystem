package com.tejosaran.bugtracking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejosaran.bugtracking.dto.ProjectMembersDTO;
import com.tejosaran.bugtracking.service.ProjectMembersService;

@RestController
@RequestMapping("/api/project-members")
@CrossOrigin

public class ProjectMembersController {
    @Autowired
    private ProjectMembersService membersService;

    // Add a user to a project
    @PostMapping("/add")
    public ResponseEntity<ProjectMembersDTO> addMember(@RequestBody Map<String, Object> request) {
        Long projectId = Long.valueOf(request.get("projectId").toString());
        Long userId = Long.valueOf(request.get("userId").toString());

        ProjectMembersDTO added = membersService.addMember(projectId, userId);
        return ResponseEntity.ok(added);
    }

    // Get all members of a project
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectMembersDTO>> getMembersByProject(@PathVariable Long projectId) {
        List<ProjectMembersDTO> members = membersService.getMembersByProject(projectId);
        return ResponseEntity.ok(members);
    }

    // Get all projects for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ProjectMembersDTO>> getProjectsByUser(@PathVariable Long userId) {
        List<ProjectMembersDTO> projects = membersService.getProjectsByUser(userId);
        return ResponseEntity.ok(projects);
    }
}
