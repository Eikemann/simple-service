package com.company.simpleservice.dto.response;

import com.company.simpleservice.models.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private Long hotelId;
    private Long roomId;
    private String guestName;
    private String guestEmail;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
}
