package com.tejosaran.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejosaran.bugtracking.Repository.CommentsRepository;
import com.tejosaran.bugtracking.dto.CommentsDTO;
import com.tejosaran.bugtracking.entity.CommentsEntity;
import com.tejosaran.bugtracking.mapper.CommentsMapper;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    // Get all comments
    public List<CommentsDTO> getAllComments() {
        return commentsRepository.findAll()
                .stream()
                .map(CommentsMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get comment by ID
    public CommentsDTO getCommentById(Long id) {
        Optional<CommentsEntity> opt = commentsRepository.findById(id);
        if (opt.isEmpty()) {
            throw new RuntimeException("Comment not found with id: " + id);
        }
        return CommentsMapper.toDTO(opt.get());
    }

    // Add new comment
    public CommentsDTO addComment(CommentsDTO dto) {
        CommentsEntity entity = CommentsMapper.toEntity(dto);
        CommentsEntity saved = commentsRepository.save(entity);
        return CommentsMapper.toDTO(saved);
    }

    // Delete comment
    public void deleteComment(Long id) {
        if (!commentsRepository.existsById(id)) {
            throw new RuntimeException("Comment not found with id: " + id);
        }
        commentsRepository.deleteById(id);
    }

    public List<CommentsDTO> getCommentsByTicket(Long ticketId) {
        List<CommentsEntity> comments = commentsRepository.findByTicketId(ticketId);
        return comments.stream()
                .map(CommentsMapper::toDTO) // assuming you have CommentsMapper
                .collect(Collectors.toList());
    }
}
