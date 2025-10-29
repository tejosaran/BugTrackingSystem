package com.tejosaran.bugtracking.controller;

import com.tejosaran.bugtracking.dto.*;
import com.tejosaran.bugtracking.entity.UsersEntity;
import com.tejosaran.bugtracking.service.UsersService;
import com.tejosaran.bugtracking.security.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ======== REGISTER ========
    @PostMapping("/register")
    public ResponseEntity<UsersDTO> registerUser(@RequestBody UserRegisterDTO dto) {

        // Encode password before saving
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Register user using service
        UsersEntity savedUser = usersService.registerUser(dto);

        // Convert to DTO to send to client
        UsersDTO responseDto = usersService.convertToDTO(savedUser);

        return ResponseEntity.ok(responseDto);
    }

    // ======== LOGIN ========
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody UserLoginDTO dto) {

        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsernameOrEmail(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT
        String token = jwtUtils.generateJwtToken(authentication);

        // Get user details
        UsersDTO userDto = usersService.getUserDTOByUsernameOrEmail(dto.getUsernameOrEmail());

        // Build response
        UserLoginResponseDTO response = new UserLoginResponseDTO(token, "Login successful", userDto);

        return ResponseEntity.ok(response);
    }
}
