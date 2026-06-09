package com.company.simpleservice.services;

import com.company.simpleservice.dto.request.Auth.LoginRequest;
import com.company.simpleservice.dto.request.Auth.RegisterRequest;
import com.company.simpleservice.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
