package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.RoomResponse;
import com.company.simpleservice.models.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomResponse toResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .hotelId(room.getHotel().getId())
                .roomNumber(room.getRoomNumber())
                .roomType(room.getRoomType())
                .capacity(room.getCapacity())
                .pricePerNight(room.getPricePerNight())
                .status(room.getStatus())
                .build();
    }
}
