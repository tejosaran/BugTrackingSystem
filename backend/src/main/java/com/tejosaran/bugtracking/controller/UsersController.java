package com.tejosaran.bugtracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejosaran.bugtracking.dto.UsersDTO;
import com.tejosaran.bugtracking.entity.RolesEntity;
import com.tejosaran.bugtracking.service.UsersService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin

public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> allUsers = usersService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/{userId}/roles")
    public ResponseEntity<List<RolesEntity>> getUserRoles(@PathVariable Long userId) {
        List<RolesEntity> roles = usersService.getRolesForUser(userId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/developers")
    public ResponseEntity<List<UsersDTO>> getAllDevelopers() {
        List<UsersDTO> developers = usersService.getAllDevelopers();
        return ResponseEntity.ok(developers);
    }

    @GetMapping("/developers/{projectId}")
    public ResponseEntity<List<UsersDTO>> getDevelopersByProject(@PathVariable Long projectId) {
        List<UsersDTO> developers = usersService.getDevelopersByProject(projectId);
        return ResponseEntity.ok(developers);
    }

    @GetMapping("/not-in-project/{projectId}")
    public ResponseEntity<List<UsersDTO>> getUsersNotInProject(@PathVariable Long projectId) {
        List<UsersDTO> users = usersService.getUsersNotInProject(projectId);
        return ResponseEntity.ok(users);
    }

}
