package com.company.simpleservice.repository;

import com.company.simpleservice.models.Order;
import com.company.simpleservice.models.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByPropertyId(Long propertyId);
    List<Order> findByRoomId(Long roomId);
    List<Order> findByUserId(Long userId);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN TRUE ELSE FALSE END FROM Order o " +
           "WHERE o.room.id = :roomId AND o.orderStatus <> :cancelled " +
           "AND o.checkInDate < :checkOut AND o.checkOutDate > :checkIn")
    boolean isRoomBooked(@Param("roomId") Long roomId,
                         @Param("checkIn") LocalDate checkIn,
                         @Param("checkOut") LocalDate checkOut,
                         @Param("cancelled") OrderStatus cancelled);
}
