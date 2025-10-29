package com.tejosaran.bugtracking.mapper;

import java.time.LocalDateTime;

import org.springframework.beans.BeanUtils;

import com.tejosaran.bugtracking.dto.CommentsDTO;
import com.tejosaran.bugtracking.dto.ProjectsDTO;
import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.CommentsEntity;
import com.tejosaran.bugtracking.entity.TicketsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;

public class CommentsMapper {
    // Entity → DTO
    public static CommentsDTO toDTO(CommentsEntity entity) {
        if (entity == null)
            return null;

        CommentsDTO dto = new CommentsDTO();

        BeanUtils.copyProperties(entity, dto); // copies id, content, etc.

        // Ticket ID

        if (entity.getTicket() != null) {
            dto.setTicketId(entity.getTicket().getId());
            TicketsDTO ticketDto = new TicketsDTO();
            ticketDto.setId(entity.getTicket().getId());
            ticketDto.setTitle(entity.getTicket().getTitle());
            ticketDto.setDescription(entity.getTicket().getDescription());
            ticketDto.setStatus(entity.getTicket().getStatus() != null ? entity.getTicket().getStatus().name() : null);

            ticketDto.setComments(null); // break recursion
            dto.setTicket(ticketDto);
        }

        // Author info
        if (entity.getAuthor() != null) {
            UsersEntity author = entity.getAuthor();
            dto.setAuthorId(author.getId());
            // dto.setAuthorName(author.getName());
            // dto.setAuthorRole(author.getRole() != null ? author.getRole().name() : null);
            dto.setAuthor(UsersMapper.toDTO(author));

        }

        // Timestamps
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt() : LocalDateTime.now());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public static CommentsEntity toEntity(CommentsDTO dto) {
        CommentsEntity entity = new CommentsEntity();
        entity.setId(dto.getId());
        entity.setContent(dto.getContent());

        // Only set IDs
        TicketsEntity ticket = new TicketsEntity();
        ticket.setId(dto.getTicketId());
        entity.setTicket(ticket);

        UsersEntity author = new UsersEntity();
        author.setId(dto.getAuthorId());
        entity.setAuthor(author);

        entity.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        entity.setUpdatedAt(dto.getUpdatedAt());

        return entity;
    }

    // Update existing entity from DTO
    public static void updateEntity(CommentsEntity entity, CommentsDTO dto) {
        if (entity == null || dto == null)
            return;

        try {
            // Copy only content (other fields should remain unchanged)
            entity.setContent(dto.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }

        entity.setUpdatedAt(LocalDateTime.now());
    }
}
