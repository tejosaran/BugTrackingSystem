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
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Table(name = "projects")

public class ProjectsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private UsersEntity createdBy;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('ACTIVE', 'INACTIVE')")
    private ProjectStatus status = ProjectStatus.ACTIVE;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // === BIDIRECTIONAL RELATIONSHIPS ===

    // One Project can have Many Members
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectMembersEntity> projectMembers = new ArrayList<>();

    // One Project can have Many Tickets
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketsEntity> tickets = new ArrayList<>();

    // Enum for status
    public enum ProjectStatus {
        ACTIVE, INACTIVE
    }

    // Constructor without id and timestamps
    public ProjectsEntity(String name, String description, UsersEntity createdBy) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
    }

    // Helper methods for bidirectional relationship management
    public void addProjectMember(ProjectMembersEntity projectMember) {
        projectMembers.add(projectMember);
        projectMember.setProject(this);
    }

    public void removeProjectMember(ProjectMembersEntity projectMember) {
        projectMembers.remove(projectMember);
        projectMember.setProject(null);
    }

    public void addTicket(TicketsEntity ticket) {
        tickets.add(ticket);
        ticket.setProject(this);
    }

    public void removeTicket(TicketsEntity ticket) {
        tickets.remove(ticket);
        ticket.setProject(null);
    }
}