package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.RoomStatus;
import com.company.simpleservice.models.RoomType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RoomResponse {
    private Long id;
    private Long hotelId;
    private String roomNumber;
    private RoomType roomType;
    private Integer capacity;
    private BigDecimal pricePerNight;
    private RoomStatus status;
}
