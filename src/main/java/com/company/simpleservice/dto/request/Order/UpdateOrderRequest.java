package com.company.simpleservice.dto.request.Order;

import com.company.simpleservice.models.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateOrderRequest(
        @NotNull(message = "orderStatus is required") OrderStatus orderStatus
) {}
