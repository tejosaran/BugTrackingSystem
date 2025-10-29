package com.tejosaran.bugtracking.controller;

import com.tejosaran.bugtracking.dto.TicketHistoryDTO;
import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.TicketsEntity.TicketStatus;
import com.tejosaran.bugtracking.service.TicketsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketsController {

    @Autowired
    private TicketsService ticketsService;

    // Get all tickets
    @GetMapping
    public List<TicketsDTO> getAllTickets() {
        return ticketsService.getAllTickets();
    }

    // Get single ticket by ID
    @GetMapping("/{id}")
    public TicketsDTO getTicketById(@PathVariable Long id) {
        return ticketsService.getTicketById(id);
    }

    // Create a new ticket
    @PostMapping
    public TicketsDTO createTicket(@RequestBody TicketsDTO ticketDTO) {
        return ticketsService.createTicket(ticketDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTicket(@PathVariable Long id) {
        boolean deleted = ticketsService.deleteTicket(id);
        if (deleted) {
            return ResponseEntity.ok("Ticket deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ticket with id " + id + " not found");
        }
    }

    @PostMapping("/update-status")
    public ResponseEntity<TicketHistoryDTO> updateStatus(@RequestBody Map<String, Object> request) {
        Long ticketId = Long.valueOf(request.get("ticketId").toString());
        Long changedById = Long.valueOf(request.get("changedById").toString());
        String newStatus = request.get("newStatus").toString();

        TicketHistoryDTO historyEntry = ticketsService.updateTicketStatus(ticketId, changedById, newStatus);
        return ResponseEntity.ok(historyEntry);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketsDTO>> getTicketsByUser(@PathVariable Long userId) {
        List<TicketsDTO> tickets = ticketsService.getTicketsByUser(userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TicketsDTO>> getTicketsByProject(@PathVariable Long projectId) {
        List<TicketsDTO> tickets = ticketsService.getTicketsByProject(projectId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketsDTO>> getTicketsByStatus(@PathVariable String status) {
        List<TicketsDTO> tickets = ticketsService.getTicketsByStatus(status);
        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/assign")
    public ResponseEntity<TicketsDTO> assignTicket(@RequestBody Map<String, Object> request) {
        Long ticketId = Long.valueOf(request.get("ticketId").toString());
        Long assigneeId = Long.valueOf(request.get("assigneeId").toString());

        TicketsDTO updatedTicket = ticketsService.assignTicket(ticketId, assigneeId);
        return ResponseEntity.ok(updatedTicket);
    }
}
