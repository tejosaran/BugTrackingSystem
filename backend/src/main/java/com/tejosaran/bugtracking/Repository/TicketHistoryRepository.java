package com.tejosaran.bugtracking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.TicketHistoryEntity;

@Repository
public interface TicketHistoryRepository extends JpaRepository<TicketHistoryEntity, Long> {
    List<TicketHistoryEntity> findByTicketIdOrderByChangeDateDesc(Long ticketId);
}
