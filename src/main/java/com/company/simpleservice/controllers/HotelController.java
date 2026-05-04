package com.company.simpleservice.controllers;

import com.company.simpleservice.dto.request.Hotel.CreateHotelRequest;
import com.company.simpleservice.dto.request.Hotel.UpdateHotelRequest;
import com.company.simpleservice.dto.response.HotelResponse;
import com.company.simpleservice.services.HotelService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotel", description = "Hotel Managing")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    public void create(@Valid @RequestBody CreateHotelRequest request) {
        hotelService.create(request);
    }

    @GetMapping
    public List<HotelResponse> findAll() {
        return hotelService.findAll();
    }

    @GetMapping("/{id}")
    public HotelResponse findById(@PathVariable long id) {
        return hotelService.findById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody UpdateHotelRequest request) {
        hotelService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
