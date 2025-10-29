package com.tejosaran.bugtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsDTO {
    private Long id;
    private String name;
    private String description;
    private String status = "ACTIVE"; // default active
    private Long createdById; // ID of the user who created the project
    private UsersDTO createdBy;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;
    private List<TicketsDTO> tickets;

    // Constructor for creation
    public ProjectsDTO(String name, String description, Long createdById, UsersDTO createdBy,
            List<TicketsDTO> tickets) {
        this.name = name;
        this.description = description;
        this.createdById = createdById;
        this.createdBy = createdBy;
        this.tickets = tickets;
        this.status = "ACTIVE";
        this.createdAt = LocalDateTime.now();
    }
}