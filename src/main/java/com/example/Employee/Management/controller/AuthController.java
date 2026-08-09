package com.example.Employee.Management.controller;

import com.example.Employee.Management.dto.request.LoginRequest;
import com.example.Employee.Management.dto.request.RefreshTokenRequest;
import com.example.Employee.Management.dto.request.RegisterRequest;
import com.example.Employee.Management.dto.response.ApiResponse;
import com.example.Employee.Management.dto.response.AuthResponse;
import com.example.Employee.Management.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")

public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> register(
            @Valid
            @RequestBody RegisterRequest request
    ) {

        String response = authService.register(request);

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("User registered successfully")
                        .data(response)
                        .build()
        );
    }
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid
            @RequestBody LoginRequest request
    ) {

        AuthResponse response = authService.login(request);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Login successful")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @Valid
            @RequestBody RefreshTokenRequest request
    ) {

        AuthResponse response = authService.refreshToken(request);

        return ResponseEntity.ok(
                ApiResponse.<AuthResponse>builder()
                        .success(true)
                        .message("Access token refreshed successfully")
                        .data(response)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            Authentication authentication
    ) {

        authService.logout(authentication.getName());

        return ResponseEntity.ok(
                ApiResponse.<String>builder()
                        .success(true)
                        .message("Logout successful")
                        .data("Refresh token deleted successfully")
                        .build()
        );
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        return ResponseEntity.ok(
                authentication.getAuthorities()
        );
    }
}