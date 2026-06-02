package com.company.simpleservice.dto.request.Room;

import com.company.simpleservice.models.RoomStatus;
import com.company.simpleservice.models.RoomType;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateRoomRequest(
        @NotBlank(message = "roomNumber is required") String roomNumber,
        @NotNull(message = "roomType is required") RoomType roomType,
        @NotNull(message = "capacity is required") @Min(1) Integer capacity,
        @NotNull(message = "pricePerNight is required") @DecimalMin(value = "0.0", inclusive = false) BigDecimal pricePerNight,
        @NotNull(message = "status is required") RoomStatus status
) {}
