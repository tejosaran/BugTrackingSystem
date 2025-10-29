package com.tejosaran.bugtracking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMembersDTO {
    private Long id;

    private Long projectId; // raw project id
    private ProjectsDTO project; // full project DTO

    private Long userId; // raw user id
    private UsersDTO user; // full user DTO

    private LocalDateTime joinedAt;

}
