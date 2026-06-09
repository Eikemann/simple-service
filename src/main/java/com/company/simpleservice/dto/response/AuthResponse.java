package com.company.simpleservice.dto.response;

public record AuthResponse(
        String token,
        Long userId,
        String email,
        String fullName,
        String role
) {}
