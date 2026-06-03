package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Room.CreateRoomRequest;
import com.company.simpleservice.dto.request.Room.UpdateRoomRequest;
import com.company.simpleservice.dto.response.RoomResponse;
import com.company.simpleservice.exceptions.ResourceNotFoundException;
import com.company.simpleservice.mapper.RoomMapper;
import com.company.simpleservice.models.Property;
import com.company.simpleservice.models.Room;
import com.company.simpleservice.repository.PropertyRepository;
import com.company.simpleservice.repository.RoomRepository;
import com.company.simpleservice.services.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PropertyRepository propertyRepository;
    private final RoomMapper roomMapper;

    @Override
    public void create(CreateRoomRequest request) {
        Property property = propertyRepository.findById(request.propertyId())
                .orElseThrow(() -> new ResourceNotFoundException(request.propertyId()));
        Room room = Room.builder()
                .property(property)
                .roomNumber(request.roomNumber())
                .roomType(request.roomType())
                .capacity(request.capacity())
                .pricePerNight(request.pricePerNight())
                .status(request.status())
                .build();
        roomRepository.save(room);
    }

    @Override
    public void update(Long id, UpdateRoomRequest request) {
        Room room = getRoomOrThrow(id);
        room.setRoomNumber(request.roomNumber());
        room.setRoomType(request.roomType());
        room.setCapacity(request.capacity());
        room.setPricePerNight(request.pricePerNight());
        room.setStatus(request.status());
        roomRepository.save(room);
    }

    @Override
    public void delete(Long id) {
        roomRepository.delete(getRoomOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> findAll() {
        return roomRepository.findAll()
                .stream().map(roomMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse findById(Long id) {
        return roomMapper.toResponse(getRoomOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> findByPropertyId(Long propertyId) {
        return roomRepository.findByPropertyId(propertyId)
                .stream().map(roomMapper::toResponse).toList();
    }

    private Room getRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
