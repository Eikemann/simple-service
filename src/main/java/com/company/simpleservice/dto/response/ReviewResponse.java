package com.company.simpleservice.dto.response;

import java.time.LocalDateTime;

public record ReviewResponse(
        Long id,
        Long hotelId,
        Integer rating,
        String title,
        String comment,
        String reviewerName,
        LocalDateTime createdAt
) {}
