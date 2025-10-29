package com.tejosaran.bugtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private Long id;
    private String content;
    private Long ticketId;
    private TicketsDTO ticket;
    private Long authorId;

    private UsersDTO author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}