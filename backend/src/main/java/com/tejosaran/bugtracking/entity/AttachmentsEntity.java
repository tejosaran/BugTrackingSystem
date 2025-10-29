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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attachments")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class AttachmentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String filename;

    @Column(name = "file_path", nullable = false, length = 500)
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "mime_type", length = 100)
    private String mimeType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketsEntity ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", nullable = false)
    private UsersEntity uploadedBy;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt = LocalDateTime.now();

    // Constructor without id and timestamp
    public AttachmentsEntity(String filename, String filePath, Long fileSize, String mimeType, TicketsEntity ticket,
            UsersEntity uploadedBy) {
        this.filename = filename;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.mimeType = mimeType;
        this.ticket = ticket;
        this.uploadedBy = uploadedBy;
    }

    // Constructor for basic file info
    public AttachmentsEntity(String filename, String filePath, String mimeType, TicketsEntity ticket,
            UsersEntity uploadedBy) {
        this.filename = filename;
        this.filePath = filePath;
        this.mimeType = mimeType;
        this.ticket = ticket;
        this.uploadedBy = uploadedBy;
    }
}