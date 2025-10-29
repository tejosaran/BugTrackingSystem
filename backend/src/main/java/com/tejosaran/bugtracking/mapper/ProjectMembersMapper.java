package com.tejosaran.bugtracking.mapper;

import com.tejosaran.bugtracking.dto.ProjectMembersDTO;
import com.tejosaran.bugtracking.entity.ProjectMembersEntity;

public class ProjectMembersMapper {
    public static ProjectMembersDTO toDTO(ProjectMembersEntity entity) {
        if (entity == null)
            return null;

        ProjectMembersDTO dto = new ProjectMembersDTO();
        dto.setId(entity.getId());

        // IDs
        dto.setProjectId(entity.getProject().getId());
        dto.setUserId(entity.getUser().getId());

        // Full DTOs
        dto.setProject(ProjectsMapper.toDTO(entity.getProject()));
        dto.setUser(UsersMapper.toDTO(entity.getUser()));

        dto.setJoinedAt(entity.getJoinedAt());
        return dto;
    }

    public static ProjectMembersEntity toEntity(ProjectMembersDTO dto,
            com.tejosaran.bugtracking.entity.ProjectsEntity project,
            com.tejosaran.bugtracking.entity.UsersEntity user) {
        if (dto == null)
            return null;

        ProjectMembersEntity entity = new ProjectMembersEntity();
        entity.setProject(project);
        entity.setUser(user);
        entity.setJoinedAt(dto.getJoinedAt() != null ? dto.getJoinedAt() : java.time.LocalDateTime.now());
        return entity;
    }
}
