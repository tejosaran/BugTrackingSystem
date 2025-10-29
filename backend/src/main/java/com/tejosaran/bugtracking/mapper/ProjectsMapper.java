package com.tejosaran.bugtracking.mapper;

import java.time.LocalDateTime;

import com.tejosaran.bugtracking.dto.ProjectsCreateDTO;
import com.tejosaran.bugtracking.dto.ProjectsDTO;
import com.tejosaran.bugtracking.entity.ProjectsEntity;
import com.tejosaran.bugtracking.entity.ProjectsEntity.ProjectStatus;
import com.tejosaran.bugtracking.entity.UsersEntity;

public class ProjectsMapper {

    public static ProjectsDTO toDTO(ProjectsEntity entity) {
        ProjectsDTO dto = new ProjectsDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());

        // assign default ACTIVE if null
        if (entity.getStatus() != null) {
            dto.setStatus(entity.getStatus().name()); // convert enum to String
        } else {
            dto.setStatus("ACTIVE"); // default if null
        }

        if (entity.getCreatedBy() != null) {
            dto.setCreatedById(entity.getCreatedBy().getId());
            dto.setCreatedBy(UsersMapper.toDTO(entity.getCreatedBy()));
        }
        // Map tickets
        if (entity.getTickets() != null) {
            dto.setTickets(entity.getTickets().stream()
                    .map(TicketsMapper::toDTO)
                    .toList());
        }
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

    // DTO → Entity (for creation)
    public static ProjectsEntity toEntity(ProjectsCreateDTO dto, UsersEntity createdBy) {
        ProjectsEntity entity = new ProjectsEntity();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        // Set the user reference
        entity.setCreatedBy(createdBy);

        // Set default status as enum
        entity.setStatus(ProjectStatus.ACTIVE); // replace ProjectStatus with your enum class name

        // Set creation timestamp
        entity.setCreatedAt(LocalDateTime.now());

        return entity;
    }

    // Update existing entity from DTO
    public static void updateEntity(ProjectsEntity entity, ProjectsCreateDTO dto) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setUpdatedAt(LocalDateTime.now()); // set update timestamp
    }
}
