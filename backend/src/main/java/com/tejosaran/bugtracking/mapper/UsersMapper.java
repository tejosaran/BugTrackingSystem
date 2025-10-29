package com.tejosaran.bugtracking.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.tejosaran.bugtracking.dto.RolesDTO;
import com.tejosaran.bugtracking.dto.UsersDTO;
import com.tejosaran.bugtracking.entity.UsersEntity;

public class UsersMapper {

    // Entity → DTO
    public static UsersDTO toDTO(UsersEntity entity) {
        if (entity == null)
            return null;

        UsersDTO dto = new UsersDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getRoles() != null && !entity.getRoles().isEmpty()) {
            dto.setRoles(
                    entity.getRoles().stream()
                            .map(role -> {
                                RolesDTO r = new RolesDTO();
                                r.setId(role.getId());
                                r.setName(role.getName());
                                return r;
                            })
                            .collect(Collectors.toSet()));
        }

        return dto;
    }

    // DTO → Entity
    public static UsersEntity toEntity(UsersDTO dto) {
        if (dto == null)
            return null;

        UsersEntity user = new UsersEntity();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setIsActive(dto.getIsActive());
        user.setCreatedAt(dto.getCreatedAt());
        user.setUpdatedAt(dto.getUpdatedAt());
        // Role mapping must be done via RoleService or repository to fetch RolesEntity
        return user;
    }
}
