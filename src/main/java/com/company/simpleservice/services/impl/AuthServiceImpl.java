package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Auth.LoginRequest;
import com.company.simpleservice.dto.request.Auth.RegisterRequest;
import com.company.simpleservice.dto.response.AuthResponse;
import com.company.simpleservice.models.User;
import com.company.simpleservice.repository.UserRepository;
import com.company.simpleservice.security.JwtUtil;
import com.company.simpleservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();
        userRepository.save(user);
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFullName(), user.getRole().name());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        User user = userRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token, user.getId(), user.getEmail(), user.getFullName(), user.getRole().name());
    }
}
