package com.tejosaran.bugtracking.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolesDTO {
    private Long id;
    private String name;

    private List<UsersDTO> users;

    public RolesDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
