package com.company.simpleservice.dto.request.Order;

import com.company.simpleservice.models.OrderStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateOrderRequest(
        @NotBlank(message = "guestName is required") String guestName,
        @NotBlank(message = "guestEmail is required") @Email(message = "guestEmail must be valid") String guestEmail,
        @NotNull(message = "checkInDate is required") LocalDate checkInDate,
        @NotNull(message = "checkOutDate is required") LocalDate checkOutDate,
        @NotNull(message = "totalAmount is required") @DecimalMin(value = "0.0", inclusive = false) BigDecimal totalAmount,
        @NotNull(message = "orderStatus is required") OrderStatus orderStatus
) {}
