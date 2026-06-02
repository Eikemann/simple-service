package com.company.simpleservice.dto.request.Hotel;

import jakarta.validation.constraints.*;

public record UpdateHotelRequest(
        @NotBlank(message = "name is required") @Size(min = 2, max = 255) String name,
        @NotBlank(message = "address is required") String address,
        @NotBlank(message = "city is required") String city,
        @NotBlank(message = "country is required") String country,
        String phone,
        @Email(message = "email must be valid") String email,
        @Min(1) @Max(5) Integer starRating
) {}
