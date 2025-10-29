package com.tejosaran.bugtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketsDTO {
    private Long id;
    private String title;
    private String description;
    private String status = "OPEN"; // default

    private Long projectId;
    private ProjectsDTO project;
    private Long reporterId;
    private UsersDTO reporter;
    private String reporterName;
    private Long assigneeId;
    private UsersDTO assignee;
    private String assigneeName;
    private LocalDateTime createdAt = LocalDateTime.now(); // default now
    private LocalDateTime updatedAt;
    private List<CommentsDTO> comments;
    private Long commentCount = 0L; // default 0
    private Long attachmentCount = 0L; // default 0

    // Optional constructor for creation
    public TicketsDTO(String title, String description, Long projectId, Long reporterId, Long assigneeId) {
        this.title = title;
        this.description = description;
        this.projectId = projectId;
        this.reporterId = reporterId;
        this.assigneeId = assigneeId;
        this.status = "OPEN";

        this.createdAt = LocalDateTime.now();
        this.commentCount = 0L;
        this.attachmentCount = 0L;
    }
}
