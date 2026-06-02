package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.ClientStatus;
import com.company.simpleservice.models.Gender;

import java.time.LocalDateTime;

public record ClientResponse(
        Long id,
        String fullName,
        Gender gender,
        ClientStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
