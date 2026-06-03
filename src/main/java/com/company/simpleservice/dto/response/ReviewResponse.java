package com.company.simpleservice.dto.response;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long propertyId,
        Long userId,
        String userFullName,
        Integer rating,
        String title,
        String comment,
        LocalDateTime createdAt
) {}
