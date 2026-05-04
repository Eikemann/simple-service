package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.ReviewResponse;
import com.company.simpleservice.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .hotelId(review.getHotel().getId())
                .rating(review.getRating())
                .title(review.getTitle())
                .comment(review.getComment())
                .reviewerName(review.getReviewerName())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
