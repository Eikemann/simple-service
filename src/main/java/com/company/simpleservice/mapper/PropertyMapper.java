package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.AmenityResponse;
import com.company.simpleservice.dto.response.PropertyResponse;
import com.company.simpleservice.models.Property;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PropertyMapper {

    public PropertyResponse toResponse(Property property,
                                       Double rating,
                                       int reviewCount,
                                       BigDecimal pricePerNight) {
        List<AmenityResponse> amenities = property.getAmenities().stream()
                .map(a -> new AmenityResponse(a.getId(), a.getName()))
                .toList();
        return new PropertyResponse(
                property.getId(),
                property.getName(),
                property.getDescription(),
                property.getAddress(),
                property.getCity(),
                property.getCountry(),
                property.getPhone(),
                property.getEmail(),
                property.getStarRating(),
                property.getPropertyType(),
                property.getImageUrl(),
                amenities,
                rating,
                reviewCount,
                pricePerNight,
                property.getCreatedAt()
        );
    }
}
