package com.tejosaran.bugtracking.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.AttachmentsEntity;

@Repository
public interface AttachmentsRepository extends JpaRepository<AttachmentsEntity, Long> {

    List<AttachmentsEntity> findByTicket_Id(Long ticketId);

    List<AttachmentsEntity> findByUploadedBy_Id(Long userId);

    Optional<AttachmentsEntity> findByFilePath(String filePath);

    @Query("SELECT a FROM AttachmentsEntity a JOIN FETCH a.uploadedBy WHERE a.ticket.id= :ticketId")
    List<AttachmentsEntity> findByTicketIdWithUploader(Long ticketId);

    long countByTicket_Id(Long ticketId);

    // Find attachments by file type
    @Query("SELECT a FROM AttachmentsEntity a WHERE a.mimeType LIKE :mimeTypePattern AND a.ticket.id = :ticketId")
    List<AttachmentsEntity> findByMimeTypeAndTicketId(String mimeTypePattern, Long ticketId);

    // Find large files
    @Query("SELECT a FROM AttachmentsEntity a WHERE a.fileSize > :sizeThreshold")
    List<AttachmentsEntity> findLargeFiles(Long sizeThreshold);
}
