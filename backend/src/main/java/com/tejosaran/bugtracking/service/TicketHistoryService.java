package com.tejosaran.bugtracking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejosaran.bugtracking.Repository.TicketHistoryRepository;
import com.tejosaran.bugtracking.Repository.TicketsRepository;
import com.tejosaran.bugtracking.Repository.UsersRepository;
import com.tejosaran.bugtracking.dto.TicketHistoryDTO;
import com.tejosaran.bugtracking.entity.TicketHistoryEntity;
import com.tejosaran.bugtracking.entity.TicketsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.mapper.TicketHistoryMapper;

@Service
public class TicketHistoryService {
    @Autowired
    private TicketHistoryRepository historyRepo;

    @Autowired
    private TicketsRepository ticketRepo;

    @Autowired
    private UsersRepository userRepo;

    public List<TicketHistoryDTO> getHistoryByTicket(Long ticketId) {
        return historyRepo.findByTicketIdOrderByChangeDateDesc(ticketId)
                .stream()
                .map(TicketHistoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TicketHistoryDTO addChange(Long ticketId, Long changedById, String oldValue, String newValue) {
        // 1️⃣ Fetch ticket and user
        TicketsEntity ticket = ticketRepo.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        UsersEntity changedBy = userRepo.findById(changedById)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Create DTO manually
        TicketHistoryDTO dto = new TicketHistoryDTO();

        dto.setOldValue(oldValue);
        dto.setNewValue(newValue);
        dto.setTicketId(ticketId);
        dto.setChangedById(changedById);

        // 3️⃣ Convert DTO → Entity using mapper
        TicketHistoryEntity entity = TicketHistoryMapper.toEntity(dto, ticket, changedBy);
        entity.setTicket(ticket); // ensure FK mapping
        entity.setChangedBy(changedBy);// ensure FK mapping
        entity.setChangeDate(LocalDateTime.now());

        // 4️⃣ Save entity
        TicketHistoryEntity saved = historyRepo.save(entity);

        // 5️⃣ Convert saved entity → DTO and return
        return TicketHistoryMapper.toDTO(saved);
    }

}
