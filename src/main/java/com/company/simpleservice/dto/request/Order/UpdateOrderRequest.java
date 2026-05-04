package com.company.simpleservice.dto.request.Order;

import com.company.simpleservice.models.OrderStatus;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateOrderRequest {

    @NotBlank(message = "guestName is required")
    private String guestName;

    @NotBlank(message = "guestEmail is required")
    @Email(message = "guestEmail must be valid")
    private String guestEmail;

    @NotNull(message = "checkInDate is required")
    private LocalDate checkInDate;

    @NotNull(message = "checkOutDate is required")
    private LocalDate checkOutDate;

    @NotNull(message = "totalAmount is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalAmount;

    @NotNull(message = "orderStatus is required")
    private OrderStatus orderStatus;
}
