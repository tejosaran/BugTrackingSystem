package com.tejosaran.bugtracking.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {
        "projectMemberships",
        "createdProjects",
        "reportedTickets",
        "assignedTickets",
        "comments",
        "uploadedAttachments",
        "ticketHistoryChanges"
})
@EqualsAndHashCode(exclude = {
        "projectMemberships",
        "createdProjects",
        "reportedTickets",
        "assignedTickets",
        "comments",
        "uploadedAttachments",
        "ticketHistoryChanges"
})
public class UsersEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RolesEntity> roles = new HashSet<>();

    // === BIDIRECTIONAL RELATIONSHIPS ===

    // One User can be member of Many Projects
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProjectMembersEntity> projectMemberships = new ArrayList<>();

    // One User can create Many Projects
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private List<ProjectsEntity> createdProjects = new ArrayList<>();

    // One User can report Many Tickets
    @OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY)
    private List<TicketsEntity> reportedTickets = new ArrayList<>();

    // One User can be assigned Many Tickets
    @OneToMany(mappedBy = "assignee", fetch = FetchType.LAZY)
    private List<TicketsEntity> assignedTickets = new ArrayList<>();

    // One User can write Many Comments
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentsEntity> comments = new ArrayList<>();

    // One User can upload Many Attachments
    @OneToMany(mappedBy = "uploadedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AttachmentsEntity> uploadedAttachments = new ArrayList<>();

    // One User can make Many Ticket History changes
    @OneToMany(mappedBy = "changedBy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TicketHistoryEntity> ticketHistoryChanges = new ArrayList<>();

    // Constructor without id and timestamps
    public UsersEntity(String username, String email, String password, RolesEntity role, String firstName,
            String lastName) {
        this.username = username;
        this.email = email;
        this.password = password;
        // this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Helper methods for bidirectional relationship management
    public void addProjectMembership(ProjectMembersEntity projectMember) {
        projectMemberships.add(projectMember);
        projectMember.setUser(this);
    }

    public void addCreatedProject(ProjectsEntity project) {
        createdProjects.add(project);
        project.setCreatedBy(this);
    }

    public void addReportedTicket(TicketsEntity ticket) {
        reportedTickets.add(ticket);
        ticket.setReporter(this);
    }

    public void addAssignedTicket(TicketsEntity ticket) {
        assignedTickets.add(ticket);
        ticket.setAssignee(this);
    }

    public void removeAssignedTicket(TicketsEntity ticket) {
        assignedTickets.remove(ticket);
        ticket.setAssignee(null);
    }

    public void addComment(CommentsEntity comment) {
        comments.add(comment);
        comment.setAuthor(this);
    }

    public void addUploadedAttachment(AttachmentsEntity attachment) {
        uploadedAttachments.add(attachment);
        attachment.setUploadedBy(this);
    }

    public void addTicketHistoryChange(TicketHistoryEntity history) {
        ticketHistoryChanges.add(history);
        history.setChangedBy(this);
    }
}
