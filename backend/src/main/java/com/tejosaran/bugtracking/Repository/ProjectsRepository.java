package com.tejosaran.bugtracking.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.ProjectsEntity;

@Repository
public interface ProjectsRepository extends JpaRepository<ProjectsEntity, Long> {
    List<ProjectsEntity> findByStatus(ProjectsEntity.ProjectStatus status);

}
