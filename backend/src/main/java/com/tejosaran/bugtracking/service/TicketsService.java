package com.tejosaran.bugtracking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejosaran.bugtracking.Repository.ProjectsRepository;
import com.tejosaran.bugtracking.Repository.TicketsRepository;
import com.tejosaran.bugtracking.Repository.UsersRepository;
import com.tejosaran.bugtracking.dto.TicketHistoryDTO;
import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.ProjectsEntity;
import com.tejosaran.bugtracking.entity.TicketsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.mapper.TicketsMapper;

@Service
public class TicketsService {

    @Autowired
    private TicketsRepository ticketsRepository;

    @Autowired
    private ProjectsRepository projectsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private TicketHistoryService historyService;

    public List<TicketsDTO> getAllTickets() {
        List<TicketsEntity> tickets = ticketsRepository.findAll();
        return tickets.stream()
                .map(TicketsMapper::toDTO)
                .toList();
    }

    // ✅ Get ticket by ID
    public TicketsDTO getTicketById(Long id) {
        TicketsEntity ticket = ticketsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with ID: " + id));
        return TicketsMapper.toDTO(ticket);
    }

    public TicketsDTO createTicket(TicketsDTO dto) {
        // Validate related entities
        ProjectsEntity project = projectsRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        UsersEntity reporter = usersRepository.findById(dto.getReporterId())
                .orElseThrow(() -> new RuntimeException("Reporter not found"));

        UsersEntity assignee = null;
        if (dto.getAssigneeId() != null) {
            assignee = usersRepository.findById(dto.getAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Assignee not found"));
        }

        // Map DTO → Entity
        TicketsEntity entity = new TicketsEntity();
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());

        entity.setStatus(TicketsEntity.TicketStatus.valueOf(dto.getStatus()));
        entity.setProject(project);
        entity.setReporter(reporter);
        entity.setAssignee(assignee);

        // Save
        TicketsEntity saved = ticketsRepository.save(entity);

        // Return DTO
        return TicketsMapper.toDTO(saved);
    }

    public boolean deleteTicket(Long id) {
        Optional<TicketsEntity> ticketOpt = ticketsRepository.findById(id);
        if (ticketOpt.isPresent()) {
            ticketsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TicketHistoryDTO updateTicketStatus(Long ticketId, Long changedById, String newStatusStr) {
        // 1️⃣ Find the ticket
        TicketsEntity ticket = ticketsRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // 2️⃣ Convert string to enum
        TicketsEntity.TicketStatus newStatus;
        try {
            newStatus = TicketsEntity.TicketStatus.valueOf(newStatusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value: " + newStatusStr);
        }

        TicketsEntity.TicketStatus oldStatus = ticket.getStatus();

        // 3️⃣ Check if status is different
        if (oldStatus.equals(newStatus)) {
            throw new RuntimeException("Ticket already has status: " + newStatus.name());
        }

        // 4️⃣ Update ticket status and timestamp
        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());
        ticketsRepository.save(ticket);

        // 5️⃣ Add entry in ticket_history
        TicketHistoryDTO historyDTO = historyService.addChange(
                ticketId,
                changedById,
                oldStatus.name(),
                newStatus.name());

        return historyDTO; // return ticket history entry
    }

    // Get tickets assigned to a specific user
    public List<TicketsDTO> getTicketsByUser(Long userId) {
        UsersEntity user = usersRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<TicketsEntity> tickets = ticketsRepository.findByAssignee(user);
        return tickets.stream()
                .map(TicketsMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketsDTO> getTicketsByProject(Long projectId) {
        // Fetch tickets from DB by project ID
        List<TicketsEntity> tickets = ticketsRepository.findByProjectId(projectId);

        // Convert entities to DTOs
        return tickets.stream()
                .map(TicketsMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketsDTO> getTicketsByStatus(String statusStr) {
        TicketsEntity.TicketStatus status;
        try {
            status = TicketsEntity.TicketStatus.valueOf(statusStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid ticket status: " + statusStr);
        }

        List<TicketsEntity> tickets = ticketsRepository.findByStatus(status);
        return tickets.stream()
                .map(TicketsMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Assign a ticket to a developer
    public TicketsDTO assignTicket(Long ticketId, Long assigneeId) {
        // 1️⃣ Fetch the ticket
        TicketsEntity ticket = ticketsRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        // 2️⃣ Fetch the developer to assign
        UsersEntity assignee = usersRepository.findById(assigneeId)
                .orElseThrow(() -> new RuntimeException("Assignee not found"));

        // 3️⃣ Set assignee (reporter and project remain unchanged)
        ticket.setAssignee(assignee);
        ticket.setUpdatedAt(LocalDateTime.now());

        // 4️⃣ Save the ticket
        TicketsEntity savedTicket = ticketsRepository.save(ticket);

        // 5️⃣ Optionally, add a ticket history entry for assignment
        // historyService.addChange(ticketId, adminId, "Assignee",
        // savedTicket.getAssignee() != null ? savedTicket.getAssignee().getName() :
        // null,
        // assignee.getName());

        // 6️⃣ Convert entity → DTO
        return TicketsMapper.toDTO(savedTicket);
    }
}