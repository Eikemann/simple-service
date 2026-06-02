package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        Long hotelId,
        Long roomId,
        String guestName,
        String guestEmail,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        BigDecimal totalAmount,
        OrderStatus orderStatus,
        LocalDateTime createdAt
) {}
