package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Property.CreatePropertyRequest;
import com.company.simpleservice.dto.request.Property.UpdatePropertyRequest;
import com.company.simpleservice.dto.response.PropertyResponse;
import com.company.simpleservice.exceptions.ResourceNotFoundException;
import com.company.simpleservice.mapper.PropertyMapper;
import com.company.simpleservice.models.Amenity;
import com.company.simpleservice.models.Property;
import com.company.simpleservice.models.PropertyType;
import com.company.simpleservice.repository.AmenityRepository;
import com.company.simpleservice.repository.PropertyRepository;
import com.company.simpleservice.repository.ReviewRepository;
import com.company.simpleservice.repository.RoomRepository;
import com.company.simpleservice.services.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
class PropertyServiceImpl implements PropertyService {

    private final PropertyRepository propertyRepository;
    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final AmenityRepository amenityRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public void create(CreatePropertyRequest request) {
        Property property = Property.builder()
                .name(request.name())
                .address(request.address())
                .city(request.city())
                .country(request.country())
                .phone(request.phone())
                .email(request.email())
                .description(request.description())
                .imageUrl(request.imageUrl())
                .starRating(request.starRating())
                .propertyType(request.propertyType())
                .amenities(resolveAmenities(request.amenities()))
                .build();
        propertyRepository.save(property);
    }

    @Override
    public void update(Long id, UpdatePropertyRequest request) {
        Property property = getPropertyOrThrow(id);
        property.setName(request.name());
        property.setAddress(request.address());
        property.setCity(request.city());
        property.setCountry(request.country());
        property.setPhone(request.phone());
        property.setEmail(request.email());
        property.setDescription(request.description());
        property.setImageUrl(request.imageUrl());
        property.setStarRating(request.starRating());
        property.setPropertyType(request.propertyType());
        property.getAmenities().clear();
        property.getAmenities().addAll(resolveAmenities(request.amenities()));
        propertyRepository.save(property);
    }

    @Override
    public void delete(Long id) {
        propertyRepository.delete(getPropertyOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyResponse> findAll() {
        return propertyRepository.findAll()
                .stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PropertyResponse findById(Long id) {
        return toResponse(getPropertyOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropertyResponse> search(String city, PropertyType type) {
        if (city != null && type != null) {
            return propertyRepository.findByCityIgnoreCaseAndPropertyType(city, type)
                    .stream().map(this::toResponse).toList();
        } else if (city != null) {
            return propertyRepository.findByCityIgnoreCase(city)
                    .stream().map(this::toResponse).toList();
        } else if (type != null) {
            return propertyRepository.findByPropertyType(type)
                    .stream().map(this::toResponse).toList();
        }
        return findAll();
    }

    Property getPropertyOrThrow(Long id) {
        return propertyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private PropertyResponse toResponse(Property property) {
        Double average = reviewRepository.findAverageRatingByPropertyId(property.getId());
        Double rating = average == null ? null : Math.round(average * 10.0) / 10.0;
        int reviewCount = (int) reviewRepository.countByPropertyId(property.getId());
        BigDecimal fromPrice = roomRepository.findMinPricePerNightByPropertyId(property.getId());
        return propertyMapper.toResponse(property, rating, reviewCount, fromPrice);
    }

    private Set<Amenity> resolveAmenities(List<String> names) {
        if (names == null) {
            return new HashSet<>();
        }
        return names.stream()
                .filter(n -> n != null && !n.isBlank())
                .map(String::trim)
                .map(name -> amenityRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> amenityRepository.save(
                                Amenity.builder().name(name).build())))
                .collect(java.util.stream.Collectors.toCollection(HashSet::new));
    }
}
