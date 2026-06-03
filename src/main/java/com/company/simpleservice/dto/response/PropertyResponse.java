package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PropertyResponse(
        Long id,
        String name,
        String description,
        String address,
        String city,
        String country,
        String phone,
        String email,
        Integer starRating,
        PropertyType propertyType,
        String imageUrl,
        List<AmenityResponse> amenities,
        Double rating,
        Integer reviewCount,
        BigDecimal pricePerNight,
        LocalDateTime createdAt
) {}
