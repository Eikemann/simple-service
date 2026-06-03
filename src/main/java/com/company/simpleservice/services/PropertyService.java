package com.company.simpleservice.services;

import com.company.simpleservice.dto.request.Property.CreatePropertyRequest;
import com.company.simpleservice.dto.request.Property.UpdatePropertyRequest;
import com.company.simpleservice.dto.response.PropertyResponse;
import com.company.simpleservice.models.PropertyType;

import java.util.List;

public interface PropertyService {
    void create(CreatePropertyRequest request);
    void update(Long id, UpdatePropertyRequest request);
    void delete(Long id);
    List<PropertyResponse> findAll();
    PropertyResponse findById(Long id);
    List<PropertyResponse> search(String city, PropertyType type);
}
