package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Order.CreateOrderRequest;
import com.company.simpleservice.dto.request.Order.UpdateOrderRequest;
import com.company.simpleservice.dto.response.OrderResponse;
import com.company.simpleservice.exceptions.SubjectNotFoundException;
import com.company.simpleservice.mapper.OrderMapper;
import com.company.simpleservice.models.Hotel;
import com.company.simpleservice.models.Order;
import com.company.simpleservice.models.Room;
import com.company.simpleservice.repository.HotelRepository;
import com.company.simpleservice.repository.OrderRepository;
import com.company.simpleservice.repository.RoomRepository;
import com.company.simpleservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final OrderMapper orderMapper;

    @Override
    public void create(CreateOrderRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new SubjectNotFoundException(request.getHotelId()));
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new SubjectNotFoundException(request.getRoomId()));

        if (!room.getHotel().getId().equals(hotel.getId())) {
            throw new IllegalArgumentException("Room does not belong to the specified hotel");
        }
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }

        Order order = Order.builder()
                .hotel(hotel)
                .room(room)
                .guestName(request.getGuestName())
                .guestEmail(request.getGuestEmail())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .totalAmount(request.getTotalAmount())
                .orderStatus(request.getOrderStatus())
                .build();
        orderRepository.save(order);
    }

    @Override
    public void update(Long id, UpdateOrderRequest request) {
        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new IllegalArgumentException("checkOutDate must be after checkInDate");
        }
        Order order = getOrderOrThrow(id);
        order.setGuestName(request.getGuestName());
        order.setGuestEmail(request.getGuestEmail());
        order.setCheckInDate(request.getCheckInDate());
        order.setCheckOutDate(request.getCheckOutDate());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderStatus(request.getOrderStatus());
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
    public List<OrderResponse> findByHotelId(Long hotelId) {
        return orderRepository.findByHotelId(hotelId)
                .stream().map(orderMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> findByRoomId(Long roomId) {
        return orderRepository.findByRoomId(roomId)
                .stream().map(orderMapper::toResponse).toList();
    }

    private Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }
}
