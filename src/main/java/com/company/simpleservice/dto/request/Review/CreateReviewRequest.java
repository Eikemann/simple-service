package com.company.simpleservice.dto.request.Review;

import jakarta.validation.constraints.*;

public record CreateReviewRequest(
        @NotNull(message = "propertyId is required") Long propertyId,
        @NotNull(message = "rating is required") @Min(1) @Max(10) Integer rating,
        @NotBlank(message = "title is required") @Size(min = 2, max = 255) String title,
        @Size(max = 2000) String comment
) {}
