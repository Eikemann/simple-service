package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.RoomResponse;
import com.company.simpleservice.models.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {
    public RoomResponse toResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getProperty().getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getStatus()
        );
    }
}
