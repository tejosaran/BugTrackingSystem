package com.tejosaran.bugtracking.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_members", uniqueConstraints = @UniqueConstraint(columnNames = { "project_id", "user_id" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMembersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectsEntity project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UsersEntity user;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt = LocalDateTime.now();

    // Constructor without id and timestamp
    public ProjectMembersEntity(ProjectsEntity project, UsersEntity user) {
        this.project = project;
        this.user = user;
    }

    // Constructor with custom join date
    public ProjectMembersEntity(ProjectsEntity project, UsersEntity user, LocalDateTime joinedAt) {
        this.project = project;
        this.user = user;
        this.joinedAt = joinedAt;
    }
}