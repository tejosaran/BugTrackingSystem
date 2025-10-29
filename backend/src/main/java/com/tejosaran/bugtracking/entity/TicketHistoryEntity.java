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
@Table(name = "ticket_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", nullable = false)
    private TicketsEntity ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private UsersEntity changedBy;

    @Column(name = "old_value", length = 255)
    private String oldValue;

    @Column(name = "new_value", length = 255)
    private String newValue;

    @Column(name = "change_date")
    private LocalDateTime changeDate = LocalDateTime.now();

    // Constructor without id and changeDate
    public TicketHistoryEntity(TicketsEntity ticket, UsersEntity changedBy, String oldValue,
            String newValue) {
        this.ticket = ticket;
        this.changedBy = changedBy;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    // Constructor for status changes
    public static TicketHistoryEntity createStatusChange(TicketsEntity ticket, UsersEntity changedBy, String oldStatus,
            String newStatus) {
        return new TicketHistoryEntity(ticket, changedBy, oldStatus, newStatus);
    }

}
