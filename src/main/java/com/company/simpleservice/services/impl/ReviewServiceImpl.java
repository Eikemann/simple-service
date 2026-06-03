package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Review.CreateReviewRequest;
import com.company.simpleservice.dto.request.Review.UpdateReviewRequest;
import com.company.simpleservice.dto.response.ReviewResponse;
import com.company.simpleservice.exceptions.ResourceNotFoundException;
import com.company.simpleservice.mapper.ReviewMapper;
import com.company.simpleservice.models.Property;
import com.company.simpleservice.models.Review;
import com.company.simpleservice.models.User;
import com.company.simpleservice.repository.PropertyRepository;
import com.company.simpleservice.repository.ReviewRepository;
import com.company.simpleservice.repository.UserRepository;
import com.company.simpleservice.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public void create(CreateReviewRequest request) {
        Property property = propertyRepository.findById(request.propertyId())
                .orElseThrow(() -> new ResourceNotFoundException(request.propertyId()));
        Review review = Review.builder()
                .property(property)
                .user(currentUser())
                .rating(request.rating())
                .title(request.title())
                .comment(request.comment())
                .build();
        reviewRepository.save(review);
    }

    @Override
    public void update(Long id, UpdateReviewRequest request) {
        Review review = getReviewOrThrow(id);
        review.setRating(request.rating());
        review.setTitle(request.title());
        review.setComment(request.comment());
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
    public List<ReviewResponse> findByPropertyId(Long propertyId) {
        return reviewRepository.findByPropertyId(propertyId)
                .stream().map(reviewMapper::toResponse).toList();
    }

    private User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(0L));
    }

    private Review getReviewOrThrow(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }
}
