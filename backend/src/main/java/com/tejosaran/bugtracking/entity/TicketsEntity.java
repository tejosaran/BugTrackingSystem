package com.tejosaran.bugtracking.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class TicketsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('OPEN', 'ASSIGNED', 'IN_PROGRESS', 'RESOLVED', 'CLOSED', 'REOPENED')")
    private TicketStatus status = TicketStatus.OPEN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectsEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private UsersEntity reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id")
    private UsersEntity assignee;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // === BIDIRECTIONAL RELATIONSHIPS ===

    // One Ticket can have Many Comments
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentsEntity> comments = new ArrayList<>();

    // One Ticket can have Many Attachments
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttachmentsEntity> attachments = new ArrayList<>();

    // One Ticket can have Many History records
    @OneToMany(mappedBy = "ticket", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketHistoryEntity> ticketHistory = new ArrayList<>();

    // Enums for status and priority
    public enum TicketStatus {
        OPEN, ASSIGNED, IN_PROGRESS, RESOLVED, CLOSED, REOPENED
    }

    // Constructor without id and timestamps
    public TicketsEntity(String title, String description, ProjectsEntity project, UsersEntity reporter) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.reporter = reporter;
    }

    // Constructor with assignee
    public TicketsEntity(String title, String description, ProjectsEntity project, UsersEntity reporter,
            UsersEntity assignee) {
        this.title = title;
        this.description = description;
        this.project = project;
        this.reporter = reporter;
        this.assignee = assignee;
    }

    // Helper methods for bidirectional relationship management
    public void addComment(CommentsEntity comment) {
        comments.add(comment);
        comment.setTicket(this);
    }

    public void addAttachment(AttachmentsEntity attachment) {
        attachments.add(attachment);
        attachment.setTicket(this);
    }

    public void addTicketHistory(TicketHistoryEntity history) {
        ticketHistory.add(history);
        history.setTicket(this);
    }
}