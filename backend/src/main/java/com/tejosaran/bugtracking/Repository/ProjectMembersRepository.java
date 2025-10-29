package com.tejosaran.bugtracking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.ProjectMembersEntity;

@Repository
public interface ProjectMembersRepository extends JpaRepository<ProjectMembersEntity, Long> {
    // Get all members of a project
    List<ProjectMembersEntity> findByProjectId(Long projectId);

    // Get all projects for a user
    List<ProjectMembersEntity> findByUserId(Long userId);

    @Query("SELECT pm.project.id FROM ProjectMembersEntity pm WHERE pm.user.id = :userId")
    List<Long> findProjectIdsByUserId(Long userId);
}
