package com.company.simpleservice.dto.request.Review;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateReviewRequest {

    @NotNull(message = "rating is required")
    @Min(value = 1) @Max(value = 10)
    private Integer rating;

    @NotBlank(message = "title is required")
    @Size(min = 2, max = 255)
    private String title;

    @Size(max = 2000)
    private String comment;

    @NotBlank(message = "reviewerName is required")
    private String reviewerName;
}
