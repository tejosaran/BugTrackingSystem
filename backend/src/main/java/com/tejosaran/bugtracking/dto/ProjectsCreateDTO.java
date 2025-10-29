package com.tejosaran.bugtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectsCreateDTO {
    @NotBlank(message = "Project name is required")
    private String name;

    private String description;
    private String status = "ACTIVE";

    @NotNull(message = "Created by user ID is required")
    private Long createdBy;
}