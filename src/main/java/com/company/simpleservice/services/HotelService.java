package com.company.simpleservice.services;

import com.company.simpleservice.dto.request.Hotel.CreateHotelRequest;
import com.company.simpleservice.dto.request.Hotel.UpdateHotelRequest;
import com.company.simpleservice.dto.response.HotelResponse;

import java.util.List;

public interface HotelService {
    void create(CreateHotelRequest request);
    void update(Long id, UpdateHotelRequest request);
    void delete(Long id);
    List<HotelResponse> findAll();
    HotelResponse findById(Long id);
}
