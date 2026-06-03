package com.company.simpleservice.services;

import com.company.simpleservice.dto.request.Room.CreateRoomRequest;
import com.company.simpleservice.dto.request.Room.UpdateRoomRequest;
import com.company.simpleservice.dto.response.RoomResponse;

import java.util.List;

public interface RoomService {
    void create(CreateRoomRequest request);
    void update(Long id, UpdateRoomRequest request);
    void delete(Long id);
    List<RoomResponse> findAll();
    RoomResponse findById(Long id);
    List<RoomResponse> findByPropertyId(Long propertyId);
}
