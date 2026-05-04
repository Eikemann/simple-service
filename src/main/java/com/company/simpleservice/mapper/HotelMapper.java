package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.HotelResponse;
import com.company.simpleservice.models.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {
    public HotelResponse toResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .address(hotel.getAddress())
                .city(hotel.getCity())
                .country(hotel.getCountry())
                .phone(hotel.getPhone())
                .email(hotel.getEmail())
                .starRating(hotel.getStarRating())
                .createdAt(hotel.getCreatedAt())
                .build();
    }
}
