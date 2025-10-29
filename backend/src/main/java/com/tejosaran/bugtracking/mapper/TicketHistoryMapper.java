package com.tejosaran.bugtracking.mapper;

import java.time.LocalDateTime;

import com.tejosaran.bugtracking.dto.TicketHistoryDTO;
import com.tejosaran.bugtracking.dto.UsersDTO;
import com.tejosaran.bugtracking.entity.TicketHistoryEntity;
import com.tejosaran.bugtracking.entity.TicketsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;

public class TicketHistoryMapper {
    public static TicketHistoryDTO toDTO(TicketHistoryEntity entity) {
        TicketHistoryDTO dto = new TicketHistoryDTO();
        dto.setId(entity.getId());
        dto.setTicketId(entity.getTicket().getId());

        dto.setOldValue(entity.getOldValue());
        dto.setNewValue(entity.getNewValue());
        dto.setChangedById(entity.getChangedBy().getId());
        // dto.setChangedByName(entity.getChangedBy().getName());
        dto.setChangeDate(entity.getChangeDate());

        UsersEntity user = entity.getChangedBy();
        if (user != null) {
            dto.setChangedBy(UsersMapper.toDTO(entity.getChangedBy()));
        }

        TicketsEntity ticket = entity.getTicket();
        if (ticket != null) {
            dto.setTicket(TicketsMapper.toDTO(entity.getTicket()));
        }
        return dto;
    }

    public static TicketHistoryEntity toEntity(TicketHistoryDTO dto, TicketsEntity ticket, UsersEntity user) {
        TicketHistoryEntity entity = new TicketHistoryEntity();
        entity.setTicket(ticket);
        entity.setChangedBy(user);

        entity.setOldValue(dto.getOldValue());
        entity.setNewValue(dto.getNewValue());
        entity.setChangeDate(LocalDateTime.now());
        return entity;
    }
}
