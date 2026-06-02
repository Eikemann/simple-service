package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.OrderResponse;
import com.company.simpleservice.models.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getHotel().getId(),
                order.getRoom().getId(),
                order.getGuestName(),
                order.getGuestEmail(),
                order.getCheckInDate(),
                order.getCheckOutDate(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getCreatedAt()
        );
    }
}
