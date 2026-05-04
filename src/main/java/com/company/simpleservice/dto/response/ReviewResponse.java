package com.company.simpleservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewResponse {
    private Long id;
    private Long hotelId;
    private Integer rating;
    private String title;
    private String comment;
    private String reviewerName;
    private LocalDateTime createdAt;
}
