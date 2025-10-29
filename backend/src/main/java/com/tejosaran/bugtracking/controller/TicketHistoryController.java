package com.tejosaran.bugtracking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejosaran.bugtracking.dto.TicketHistoryDTO;
import com.tejosaran.bugtracking.service.TicketHistoryService;

@CrossOrigin
@RestController
@RequestMapping("/api/ticket-history")
public class TicketHistoryController {

    @Autowired
    private TicketHistoryService service;

    // 🔹 Get ticket history list
    @GetMapping("/{ticketId}")
    public ResponseEntity<List<TicketHistoryDTO>> getHistory(@PathVariable Long ticketId) {
        List<TicketHistoryDTO> history = service.getHistoryByTicket(ticketId);
        return ResponseEntity.ok(history);
    }

    // 🔹 Add a new change entry (when a field is updated)
    @PostMapping("/add")
    public ResponseEntity<TicketHistoryDTO> addChange(@RequestBody Map<String, Object> request) {
        Long ticketId = Long.valueOf(request.get("ticketId").toString());
        Long changedById = Long.valueOf(request.get("changedById").toString());
        String oldValue = request.get("oldValue") != null ? request.get("oldValue").toString() : null;
        String newValue = request.get("newValue") != null ? request.get("newValue").toString() : null;

        TicketHistoryDTO dto = service.addChange(ticketId, changedById, oldValue, newValue);
        return ResponseEntity.ok(dto);
    }

}
