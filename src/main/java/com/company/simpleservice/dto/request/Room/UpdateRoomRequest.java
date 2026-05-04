package com.company.simpleservice.dto.request.Room;

import com.company.simpleservice.models.RoomStatus;
import com.company.simpleservice.models.RoomType;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateRoomRequest {

    @NotBlank(message = "roomNumber is required")
    private String roomNumber;

    @NotNull(message = "roomType is required")
    private RoomType roomType;

    @NotNull(message = "capacity is required")
    @Min(value = 1)
    private Integer capacity;

    @NotNull(message = "pricePerNight is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerNight;

    @NotNull(message = "status is required")
    private RoomStatus status;
}
