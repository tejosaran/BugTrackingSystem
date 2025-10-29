package com.tejosaran.bugtracking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.CommentsEntity;

@Repository
public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {
    List<CommentsEntity> findByTicketId(Long ticketId);
}
