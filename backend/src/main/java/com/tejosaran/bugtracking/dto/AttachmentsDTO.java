package com.tejosaran.bugtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentsDTO {
    private Long id;
    private String filename;
    private String filePath;
    private Long fileSize;
    private String mimeType;
    private Long ticketId;
    private Long uploadedById;
    private String uploadedByName;
    private LocalDateTime uploadedAt;

    // Helper method to get file size in readable format
    public String getFileSizeReadable() {
        if (fileSize == null)
            return "0 B";

        if (fileSize < 1024) {
            return fileSize + " B";
        } else if (fileSize < 1024 * 1024) {
            return String.format("%.1f KB", fileSize / 1024.0);
        } else {
            return String.format("%.1f MB", fileSize / (1024.0 * 1024.0));
        }
    }
}