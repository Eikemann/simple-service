package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Hotel.CreateHotelRequest;
import com.company.simpleservice.dto.request.Hotel.UpdateHotelRequest;
import com.company.simpleservice.dto.response.HotelResponse;
import com.company.simpleservice.exceptions.ResourceNotFoundException;
import com.company.simpleservice.mapper.HotelMapper;
import com.company.simpleservice.models.Hotel;
import com.company.simpleservice.repository.HotelRepository;
import com.company.simpleservice.services.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    @Override
    public void create(CreateHotelRequest request) {
        Hotel hotel = Hotel.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .country(request.country())
                .phone(request.phone())
                .email(request.email())
                .starRating(request.starRating())
                .build();
        hotelRepository.save(hotel);
    }

    @Override
    public void update(Long id, UpdateHotelRequest request) {
        Hotel hotel = getHotelOrThrow(id);
        hotel.setName(request.name());
        hotel.setAddress(request.address());
        hotel.setCity(request.city());
        hotel.setCountry(request.country());
        hotel.setPhone(request.phone());
        hotel.setEmail(request.email());
        hotel.setStarRating(request.starRating());
        hotelRepository.save(hotel);
    }

    @Override
    public void delete(Long id) {
        Hotel hotel = getHotelOrThrow(id);
        hotelRepository.delete(hotel);
    }

    @Override
    public List<HotelResponse> findAll() {
        return hotelRepository.findAll()
                .stream().map(hotelMapper::toResponse).toList();
    }

    @Override
    public HotelResponse findById(Long id) {
        return hotelMapper.toResponse(getHotelOrThrow(id));
    }

    Hotel getHotelOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
