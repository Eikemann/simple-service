package com.company.simpleservice.dto.response;

import java.time.LocalDateTime;

public record HotelResponse(
        Long id,
        String name,
        String address,
        String city,
        String country,
        String phone,
        String email,
        Integer starRating,
        LocalDateTime createdAt
) {}