package com.tejosaran.bugtracking.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.RolesEntity;

@Repository
public interface RolesRepository extends JpaRepository<RolesEntity, Long> {

    Optional<RolesEntity> findByName(String name);
}
