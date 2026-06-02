package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.HotelResponse;
import com.company.simpleservice.models.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {
    public HotelResponse toResponse(Hotel hotel) {
        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getAddress(),
                hotel.getCity(),
                hotel.getCountry(),
                hotel.getPhone(),
                hotel.getEmail(),
                hotel.getStarRating(),
                hotel.getCreatedAt()
        );
    }
}
