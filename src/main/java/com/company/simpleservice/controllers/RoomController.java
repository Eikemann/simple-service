package com.company.simpleservice.controllers;

import com.company.simpleservice.dto.request.Room.CreateRoomRequest;
import com.company.simpleservice.dto.request.Room.UpdateRoomRequest;
import com.company.simpleservice.dto.response.RoomResponse;
import com.company.simpleservice.services.RoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
@Tag(name = "Room", description = "Room Managing")
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public void create(@Valid @RequestBody CreateRoomRequest request) {
        roomService.create(request);
    }

    @GetMapping
    public List<RoomResponse> findAll() {
        return roomService.findAll();
    }

    @GetMapping("/{id}")
    public RoomResponse findById(@PathVariable long id) {
        return roomService.findById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<RoomResponse> findByHotelId(@PathVariable long hotelId) {
        return roomService.findByHotelId(hotelId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody UpdateRoomRequest request) {
        roomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
