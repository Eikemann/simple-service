package com.company.simpleservice.repository;

import com.company.simpleservice.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByPropertyId(Long hotelId);

    @Query("select min(r.pricePerNight) from Room r where r.property.id = :propertyId")
    BigDecimal findMinPricePerNightByPropertyId(@Param("propertyId") Long propertyId);
}
