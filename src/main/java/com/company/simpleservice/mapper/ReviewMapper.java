package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.ReviewResponse;
import com.company.simpleservice.models.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {
    public ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getProperty().getId(),
                review.getUser().getId(),
                review.getUser().getFullName(),
                review.getRating(),
                review.getTitle(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
