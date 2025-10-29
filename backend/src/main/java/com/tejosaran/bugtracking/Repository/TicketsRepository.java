package com.tejosaran.bugtracking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.RolesEntity;
import com.tejosaran.bugtracking.entity.TicketsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;

@Repository
public interface TicketsRepository extends JpaRepository<TicketsEntity, Long> {

    List<TicketsEntity> findByStatus(TicketsEntity.TicketStatus status);

    List<TicketsEntity> findByProjectId(Long projectId);

    // List<TicketsEntity> findByAssigneeRole(RolesEntity role);

    // List<TicketsEntity> findByReporterRole(RolesEntity role);

    List<TicketsEntity> findByAssignee(UsersEntity assignee);

}
