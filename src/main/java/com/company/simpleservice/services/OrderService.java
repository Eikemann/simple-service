package com.company.simpleservice.services;

import com.company.simpleservice.dto.request.Order.CreateOrderRequest;
import com.company.simpleservice.dto.request.Order.UpdateOrderRequest;
import com.company.simpleservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    void create(CreateOrderRequest request);
    void update(Long id, UpdateOrderRequest request);
    void delete(Long id);
    List<OrderResponse> findAll();
    OrderResponse findById(Long id);
    List<OrderResponse> findByPropertyId(Long propertyId);
    List<OrderResponse> findByRoomId(Long roomId);
    List<OrderResponse> findMyOrders();
}
