package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Hotel.CreateHotelRequest;
import com.company.simpleservice.dto.request.Hotel.UpdateHotelRequest;
import com.company.simpleservice.dto.response.HotelResponse;
import com.company.simpleservice.exceptions.SubjectNotFoundException;
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
                .name(request.getName())
                .address(request.getAddress())
                .city(request.getCity())
                .country(request.getCountry())
                .phone(request.getPhone())
                .email(request.getEmail())
                .starRating(request.getStarRating())
                .build();
        hotelRepository.save(hotel);
    }

    @Override
    public void update(Long id, UpdateHotelRequest request) {
        Hotel hotel = getHotelOrThrow(id);
        hotel.setName(request.getName());
        hotel.setAddress(request.getAddress());
        hotel.setCity(request.getCity());
        hotel.setCountry(request.getCountry());
        hotel.setPhone(request.getPhone());
        hotel.setEmail(request.getEmail());
        hotel.setStarRating(request.getStarRating());
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
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }
}
