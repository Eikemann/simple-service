package com.company.simpleservice.repository;

import com.company.simpleservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByHotelId(Long hotelId);
    List<Order> findByRoomId(Long roomId);
}
