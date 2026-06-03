package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long propertyId,
        String propertyName,
        String propertyImageUrl,
        Long roomId,
        String roomNumber,
        Long userId,
        String userFullName,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal totalAmount,
        OrderStatus orderStatus,
        LocalDateTime createdAt
) {}
