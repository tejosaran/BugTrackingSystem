package com.tejosaran.bugtracking.service;

import com.tejosaran.bugtracking.dto.RolesDTO;
import com.tejosaran.bugtracking.dto.UsersDTO;
import com.tejosaran.bugtracking.dto.UserRegisterDTO;
import com.tejosaran.bugtracking.entity.RolesEntity;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.Repository.RolesRepository;
import com.tejosaran.bugtracking.Repository.UsersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RolesRepository rolesRepository;

    // ===== Register User =====
    public UsersEntity registerUser(UserRegisterDTO dto) {
        UsersEntity user = new UsersEntity();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setIsActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Assign role(s)
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            Set<RolesEntity> roles = dto.getRoleIds().stream()
                    .map(id -> rolesRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Role not found: " + id)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        }

        return usersRepository.save(user);
    }

    // ===== Convert UsersEntity to UsersDTO =====
    public UsersDTO convertToDTO(UsersEntity user) {
        UsersDTO dto = new UsersDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPaswword(user.getPassword()); // optional: you may omit password in DTO
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setIsActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        // Map roles
        if (user.getRoles() != null) {
            Set<RolesDTO> rolesDto = user.getRoles().stream()
                    .map(role -> new RolesDTO(role.getId(), role.getName(), null))
                    .collect(Collectors.toSet());
            dto.setRoles(rolesDto);
        }

        return dto;
    }

    // ===== Load User by username for Spring Security =====
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        UsersEntity user = usersRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + usernameOrEmail));

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    // ===== Get UserDTO by username or email =====
    public UsersDTO getUserDTOByUsernameOrEmail(String usernameOrEmail) {
        UsersEntity user = usersRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found with username/email: " + usernameOrEmail));
        return convertToDTO(user);
    }

    // ===== Get UsersEntity by username or email =====
    public UsersEntity getUserEntityByUsernameOrEmail(String usernameOrEmail) {
        return usersRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new RuntimeException("User not found with username/email: " + usernameOrEmail));
    }

    // ===== Get all users =====
    public List<UsersDTO> getAllUsers() {
        List<UsersEntity> users = usersRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<RolesEntity> getRolesForUser(Long userId) {
        return usersRepository.findRolesByUserId(userId);
    }

    public List<UsersDTO> getAllDevelopers() {
        List<UsersEntity> devs = usersRepository.findByRoleName("Developer");

        return devs.stream()
                .map(u -> new UsersDTO(
                        u.getId(),
                        u.getFirstName(),
                        u.getLastName(),
                        u.getEmail()))
                .collect(Collectors.toList());
    }

    public List<UsersDTO> getDevelopersByProject(Long projectId) {
        List<UsersEntity> developers = usersRepository.findDevelopersByProjectId(projectId);
        return developers.stream()
                .map(u -> new UsersDTO(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail()))
                .collect(Collectors.toList());
    }

    public List<UsersDTO> getUsersNotInProject(Long projectId) {
        List<UsersEntity> allUsers = usersRepository.findAll();
        List<UsersEntity> projectMembers = usersRepository.findUsersByProjectId(projectId);

        List<Long> memberIds = projectMembers.stream()
                .map(UsersEntity::getId)
                .toList();

        List<UsersEntity> notInProject = allUsers.stream()
                .filter(u -> !memberIds.contains(u.getId()))
                .toList();

        return notInProject.stream()
                .map(this::convertToDTO)
                .toList();
    }

}
