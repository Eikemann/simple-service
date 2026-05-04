package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Room.CreateRoomRequest;
import com.company.simpleservice.dto.request.Room.UpdateRoomRequest;
import com.company.simpleservice.dto.response.RoomResponse;
import com.company.simpleservice.exceptions.SubjectNotFoundException;
import com.company.simpleservice.mapper.RoomMapper;
import com.company.simpleservice.models.Hotel;
import com.company.simpleservice.models.Room;
import com.company.simpleservice.repository.HotelRepository;
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
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;

    @Override
    public void create(CreateRoomRequest request) {
        Hotel hotel = hotelRepository.findById(request.getHotelId())
                .orElseThrow(() -> new SubjectNotFoundException(request.getHotelId()));
        Room room = Room.builder()
                .hotel(hotel)
                .roomNumber(request.getRoomNumber())
                .roomType(request.getRoomType())
                .capacity(request.getCapacity())
                .pricePerNight(request.getPricePerNight())
                .status(request.getStatus())
                .build();
        roomRepository.save(room);
    }

    @Override
    public void update(Long id, UpdateRoomRequest request) {
        Room room = getRoomOrThrow(id);
        room.setRoomNumber(request.getRoomNumber());
        room.setRoomType(request.getRoomType());
        room.setCapacity(request.getCapacity());
        room.setPricePerNight(request.getPricePerNight());
        room.setStatus(request.getStatus());
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
    public List<RoomResponse> findByHotelId(Long hotelId) {
        return roomRepository.findByHotelId(hotelId)
                .stream().map(roomMapper::toResponse).toList();
    }

    private Room getRoomOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }
}
