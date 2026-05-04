package com.company.simpleservice.services;

import com.company.simpleservice.dto.request.Review.CreateReviewRequest;
import com.company.simpleservice.dto.request.Review.UpdateReviewRequest;
import com.company.simpleservice.dto.response.ReviewResponse;

import java.util.List;

public interface ReviewService {
    void create(CreateReviewRequest request);
    void update(Long id, UpdateReviewRequest request);
    void delete(Long id);
    List<ReviewResponse> findAll();
    ReviewResponse findById(Long id);
    List<ReviewResponse> findByHotelId(Long hotelId);
}
