package com.company.simpleservice.dto.request.Order;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateOrderRequest(
        @NotNull(message = "propertyId is required") Long propertyId,
        @NotNull(message = "roomId is required") Long roomId,
        @NotNull(message = "checkInDate is required") LocalDate checkInDate,
        @NotNull(message = "checkOutDate is required") LocalDate checkOutDate
) {}
