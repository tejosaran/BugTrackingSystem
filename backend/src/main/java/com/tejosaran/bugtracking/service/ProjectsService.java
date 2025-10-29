package com.tejosaran.bugtracking.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tejosaran.bugtracking.Repository.ProjectMembersRepository;
import com.tejosaran.bugtracking.Repository.ProjectsRepository;
import com.tejosaran.bugtracking.Repository.TicketsRepository;
import com.tejosaran.bugtracking.dto.ProjectsCreateDTO;
import com.tejosaran.bugtracking.dto.ProjectsDTO;
import com.tejosaran.bugtracking.dto.TicketsDTO;
import com.tejosaran.bugtracking.entity.ProjectsEntity;
import com.tejosaran.bugtracking.entity.TicketsEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.mapper.ProjectsMapper;
import com.tejosaran.bugtracking.mapper.TicketsMapper;

@Service
public class ProjectsService {
    @Autowired
    private ProjectsRepository projectsRepository;
    @Autowired
    private ProjectMembersRepository membersRepository;

    @Autowired
    private TicketsRepository ticketsRepository;

    public List<ProjectsDTO> getAllProjects() {
        return projectsRepository.findAll()
                .stream()
                .map(ProjectsMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProjectsDTO getProjectById(Long id) {
        Optional<ProjectsEntity> projectOpt = projectsRepository.findById(id);
        if (projectOpt.isEmpty()) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        return ProjectsMapper.toDTO(projectOpt.get());
    }

    public ProjectsDTO addProject(ProjectsCreateDTO dto, UsersEntity createdBy) {
        ProjectsEntity entity = ProjectsMapper.toEntity(dto, createdBy);
        ProjectsEntity saved = projectsRepository.save(entity);
        return ProjectsMapper.toDTO(saved);
    }

    public void deleteProject(Long id) {
        if (!projectsRepository.existsById(id)) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        projectsRepository.deleteById(id);
    }

    public List<ProjectsDTO> getProjectsByUser(Long userId) {
        // Fetch project IDs from project_members table for this user
        List<Long> projectIds = membersRepository.findProjectIdsByUserId(userId);

        // Fetch ProjectsEntity for these IDs
        List<ProjectsEntity> projects = projectsRepository.findAllById(projectIds);

        // Convert to DTO
        return projects.stream()
                .map(ProjectsMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TicketsDTO> getTicketsByProject(Long projectId) {
        // Fetch all tickets belonging to this project
        List<TicketsEntity> tickets = ticketsRepository.findByProjectId(projectId);

        // Convert to DTO
        return tickets.stream()
                .map(TicketsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
