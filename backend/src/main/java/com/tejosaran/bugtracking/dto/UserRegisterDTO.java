package com.tejosaran.bugtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    // Multiple roles
    private Set<Long> roleIds;
}
