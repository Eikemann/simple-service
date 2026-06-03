package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Order.CreateOrderRequest;
import com.company.simpleservice.dto.request.Order.UpdateOrderRequest;
import com.company.simpleservice.dto.response.OrderResponse;
import com.company.simpleservice.exceptions.ResourceNotFoundException;
import com.company.simpleservice.mapper.OrderMapper;
import com.company.simpleservice.models.*;
import com.company.simpleservice.repository.OrderRepository;
import com.company.simpleservice.repository.PropertyRepository;
import com.company.simpleservice.repository.RoomRepository;
import com.company.simpleservice.repository.UserRepository;
import com.company.simpleservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public void create(CreateOrderRequest request) {
        if (!request.checkOutDate().isAfter(request.checkInDate())) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }

        Property property = propertyRepository.findById(request.propertyId())
                .orElseThrow(() -> new ResourceNotFoundException(request.propertyId()));
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new ResourceNotFoundException(request.roomId()));

        if (!room.getProperty().getId().equals(property.getId())) {
            throw new IllegalArgumentException("Room does not belong to the specified property");
        }
        if (orderRepository.isRoomBooked(room.getId(), request.checkInDate(), request.checkOutDate(), OrderStatus.CANCELLED)) {
            throw new IllegalArgumentException("Room is not available for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(request.checkInDate(), request.checkOutDate());
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Order order = Order.builder()
                .property(property)
                .room(room)
                .user(currentUser())
                .checkInDate(request.checkInDate())
                .checkOutDate(request.checkOutDate())
                .totalAmount(totalAmount)
                .orderStatus(OrderStatus.PENDING)
                .build();
        orderRepository.save(order);
    }

    @Override
    public void update(Long id, UpdateOrderRequest request) {
        Order order = getOrderOrThrow(id);
        order.setOrderStatus(request.orderStatus());
        orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.delete(getOrderOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse findById(Long id) {
        return orderMapper.toResponse(getOrderOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findByPropertyId(Long propertyId) {
        return orderRepository.findByPropertyId(propertyId)
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findByRoomId(Long roomId) {
        return orderRepository.findByRoomId(roomId)
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findMyOrders() {
        return orderRepository.findByUserId(currentUser().getId())
                .stream().map(orderMapper::toResponse).toList();
    }

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(0L));
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
