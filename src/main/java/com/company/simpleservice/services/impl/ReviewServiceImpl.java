package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Review.CreateReviewRequest;
import com.company.simpleservice.dto.request.Review.UpdateReviewRequest;
import com.company.simpleservice.dto.response.ReviewResponse;
import com.company.simpleservice.exceptions.ResourceNotFoundException;
import com.company.simpleservice.mapper.ReviewMapper;
import com.company.simpleservice.models.Hotel;
import com.company.simpleservice.models.Review;
import com.company.simpleservice.repository.HotelRepository;
import com.company.simpleservice.repository.ReviewRepository;
import com.company.simpleservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final HotelRepository hotelRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public void create(CreateReviewRequest request) {
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException(request.hotelId()));
        Review review = Review.builder()
                .hotel(hotel)
                .rating(request.rating())
                .title(request.title())
                .comment(request.comment())
                .reviewerName(request.reviewerName())
                .build();
        reviewRepository.save(review);
    }

    @Override
    public void update(Long id, UpdateReviewRequest request) {
        Review review = getReviewOrThrow(id);
        review.setRating(request.rating());
        review.setTitle(request.title());
        review.setComment(request.comment());
        review.setReviewerName(request.reviewerName());
        reviewRepository.save(review);
    }

    @Override
    public void delete(Long id) {
        reviewRepository.delete(getReviewOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findAll() {
        return reviewRepository.findAll()
                .stream().map(reviewMapper::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewResponse findById(Long id) {
        return reviewMapper.toResponse(getReviewOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findByHotelId(Long hotelId) {
        return reviewRepository.findByHotelId(hotelId)
                .stream().map(reviewMapper::toResponse).toList();
    }

    private Review getReviewOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
