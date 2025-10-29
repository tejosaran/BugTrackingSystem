package com.tejosaran.bugtracking.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tejosaran.bugtracking.entity.RolesEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByUsername(String username);

    Optional<UsersEntity> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<UsersEntity> findByUsernameOrEmail(String username, String email);

    // Find users by a specific role
    List<UsersEntity> findByRoles(RolesEntity role);

    @Query("SELECT u FROM UsersEntity u JOIN u.roles r WHERE r.name = :roleName")
    List<UsersEntity> findByRoleName(@Param("roleName") String roleName);

    Optional<UsersEntity> findById(Long id);

    @Query("SELECT r FROM UsersEntity u JOIN u.roles r WHERE u.id = :userId")
    List<RolesEntity> findRolesByUserId(@Param("userId") Long userId);

    @Query("SELECT u FROM UsersEntity u " +
            "JOIN u.projectMemberships pm " +
            "JOIN u.roles r " +
            "WHERE pm.project.id = :projectId AND r.name = 'Developer'")
    List<UsersEntity> findDevelopersByProjectId(@Param("projectId") Long projectId);

    @Query("SELECT u FROM UsersEntity u JOIN ProjectMembersEntity pm ON u.id = pm.user.id WHERE pm.project.id = :projectId")
    List<UsersEntity> findUsersByProjectId(@Param("projectId") Long projectId);

}
