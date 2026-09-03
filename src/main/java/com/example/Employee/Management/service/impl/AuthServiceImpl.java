package com.example.Employee.Management.service.impl;

import com.example.Employee.Management.dto.request.LoginRequest;
import com.example.Employee.Management.dto.request.RefreshTokenRequest;
import com.example.Employee.Management.dto.request.RegisterRequest;
import com.example.Employee.Management.dto.response.AuthResponse;
import com.example.Employee.Management.dto.response.UserMeResponse;
import com.example.Employee.Management.entity.RefreshToken;
import com.example.Employee.Management.entity.Role;
import com.example.Employee.Management.entity.User;
import com.example.Employee.Management.enums.RoleType;
import com.example.Employee.Management.exception.DuplicateResourceException;
import com.example.Employee.Management.repository.RoleRepository;
import com.example.Employee.Management.repository.UserRepository;
import com.example.Employee.Management.service.AuthService;

import com.example.Employee.Management.service.RefreshTokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Employee.Management.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;


    @Override
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
           throw new DuplicateResourceException("User already registered with this email");
        }

        RoleType roleType = RoleType.valueOf(request.getRole().toUpperCase());

        Role role = roleRepository.findByName(roleType).orElseThrow(() -> new RuntimeException("Role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtService.generateToken(user.getEmail());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Transactional
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService
                .verifyRefreshToken(request.getRefreshToken());

        User user = refreshToken.getUser();

        String accessToken = jwtService.generateToken(user.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }


    @Override
    public void logout(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenService.deleteRefreshToken(user);
    }

    @Override
    public UserMeResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Long employeeId = null;

        if (user.getEmployee() != null) {
            employeeId = user.getEmployee().getId();
        }

        return UserMeResponse.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName().name())
                .employeeId(employeeId)
                .build();
    }
}
