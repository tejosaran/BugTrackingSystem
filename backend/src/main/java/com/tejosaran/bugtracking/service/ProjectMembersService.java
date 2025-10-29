package com.tejosaran.bugtracking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejosaran.bugtracking.Repository.ProjectMembersRepository;
import com.tejosaran.bugtracking.Repository.ProjectsRepository;
import com.tejosaran.bugtracking.Repository.UsersRepository;
import com.tejosaran.bugtracking.dto.ProjectMembersDTO;
import com.tejosaran.bugtracking.entity.ProjectMembersEntity;
import com.tejosaran.bugtracking.entity.ProjectsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.mapper.ProjectMembersMapper;

@Service
public class ProjectMembersService {
    @Autowired
    private ProjectMembersRepository membersRepo;

    @Autowired
    private ProjectsRepository projectRepo;

    @Autowired
    private UsersRepository userRepo;

    // Add user to project
    public ProjectMembersDTO addMember(Long projectId, Long userId) {
        ProjectsEntity project = projectRepo.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        UsersEntity user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ProjectMembersEntity entity = new ProjectMembersEntity();
        entity.setProject(project);
        entity.setUser(user);
        entity.setJoinedAt(java.time.LocalDateTime.now());

        ProjectMembersEntity saved = membersRepo.save(entity);
        return ProjectMembersMapper.toDTO(saved);
    }

    // Get all members of a project
    public List<ProjectMembersDTO> getMembersByProject(Long projectId) {
        return membersRepo.findByProjectId(projectId)
                .stream()
                .map(ProjectMembersMapper::toDTO)
                .collect(Collectors.toList());
    }

    // Get all projects for a user
    public List<ProjectMembersDTO> getProjectsByUser(Long userId) {
        return membersRepo.findByUserId(userId)
                .stream()
                .map(ProjectMembersMapper::toDTO)
                .collect(Collectors.toList());
    }

}
