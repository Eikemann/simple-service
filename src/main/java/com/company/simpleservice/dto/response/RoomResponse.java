package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.RoomStatus;
import com.company.simpleservice.models.RoomType;

import java.math.BigDecimal;

public record RoomResponse(
        Long id,
        Long propertyId,
        String roomNumber,
        RoomType roomType,
        Integer capacity,
        BigDecimal pricePerNight,
        RoomStatus status
) {}
