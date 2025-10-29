package com.tejosaran.bugtracking.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.tejosaran.bugtracking.dto.CommentsDTO;
import com.tejosaran.bugtracking.dto.ProjectsDTO;
import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.TicketsEntity;

public class TicketsMapper {

    public static TicketsDTO toDTO(TicketsEntity entity) {
        if (entity == null)
            return null;

        TicketsDTO dto = new TicketsDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStatus(entity.getStatus() != null ? entity.getStatus().name() : "OPEN");

        // Project info
        if (entity.getProject() != null) {
            ProjectsDTO pd = new ProjectsDTO();
            pd.setId(entity.getProject().getId());
            pd.setName(entity.getProject().getName());
            pd.setTickets(null); // Important: break recursion
            dto.setProject(pd);
        }

        // Reporter info
        if (entity.getReporter() != null) {
            dto.setReporterId(entity.getReporter().getId());
            dto.setReporter(UsersMapper.toDTO(entity.getReporter())); // direct mapping
        }

        // Assignee info
        if (entity.getAssignee() != null) {
            dto.setAssigneeId(entity.getAssignee().getId());
            dto.setAssignee(UsersMapper.toDTO(entity.getAssignee())); // direct mapping
        }

        if (entity.getComments() != null) {
            dto.setComments(entity.getComments().stream()
                    .map(CommentsMapper::toDTO)
                    .collect(Collectors.toList()));

        }

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        dto.setCommentCount(entity.getComments() != null ? (long) entity.getComments().size() : 0L);
        dto.setAttachmentCount(entity.getAttachments() != null ? (long) entity.getAttachments().size() : 0L);

        return dto;
    }

    // DTO → Entity (for creation)
    public static TicketsEntity toEntity(TicketsDTO dto,
            com.tejosaran.bugtracking.entity.ProjectsEntity project,
            com.tejosaran.bugtracking.entity.UsersEntity reporter,
            com.tejosaran.bugtracking.entity.UsersEntity assignee) {
        if (dto == null)
            return null;

        TicketsEntity entity = new TicketsEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setProject(project);
        entity.setReporter(reporter);
        entity.setAssignee(assignee);

        // Set enums from string values
        if (dto.getStatus() != null) {
            entity.setStatus(TicketsEntity.TicketStatus.valueOf(dto.getStatus()));
        }

        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : java.time.LocalDateTime.now());
        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }

    // Update existing entity from DTO
    public static void updateEntity(TicketsEntity entity, TicketsDTO dto) {
        if (entity == null || dto == null)
            return;

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        if (dto.getStatus() != null) {
            entity.setStatus(TicketsEntity.TicketStatus.valueOf(dto.getStatus()));
        }

        entity.setUpdatedAt(java.time.LocalDateTime.now());
    }
}