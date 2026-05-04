package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.OrderResponse;
import com.company.simpleservice.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .hotelId(order.getHotel().getId())
                .roomId(order.getRoom().getId())
                .guestName(order.getGuestName())
                .guestEmail(order.getGuestEmail())
                .checkInDate(order.getCheckInDate())
                .checkOutDate(order.getCheckOutDate())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
