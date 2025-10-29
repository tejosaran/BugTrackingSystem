package com.tejosaran.bugtracking.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoryDTO {
    private Long id;
    private Long ticketId;
    private TicketsDTO ticket;

    private String oldValue;
    private String newValue;
    private Long changedById;
    private UsersDTO changedBy;
    private String changedByName;
    private LocalDateTime changeDate;
}
