package com.tejosaran.bugtracking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejosaran.bugtracking.dto.CommentsDTO;
import com.tejosaran.bugtracking.service.CommentsService;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin

public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @GetMapping
    public ResponseEntity<List<CommentsDTO>> getAllComments() {
        return ResponseEntity.ok(commentsService.getAllComments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentsDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentsService.getCommentById(id));
    }

    @PostMapping
    public ResponseEntity<CommentsDTO> addComment(@RequestBody CommentsDTO dto) {
        return ResponseEntity.ok(commentsService.addComment(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentsService.deleteComment(id);
        return ResponseEntity.ok("Comment deleted successfully");
    }

    @GetMapping("/ticket/{ticketId}")
    public ResponseEntity<List<CommentsDTO>> getCommentsByTicket(@PathVariable Long ticketId) {
        List<CommentsDTO> comments = commentsService.getCommentsByTicket(ticketId);
        return ResponseEntity.ok(comments);
    }
}