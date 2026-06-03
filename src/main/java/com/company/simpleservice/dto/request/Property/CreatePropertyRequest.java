package com.company.simpleservice.dto.request.Property;

import com.company.simpleservice.models.PropertyType;
import jakarta.validation.constraints.*;

import java.util.List;

public record CreatePropertyRequest(
        @NotBlank(message = "name is required") @Size(min = 2, max = 255) String name,
        @NotBlank(message = "address is required") String address,
        @NotBlank(message = "city is required") String city,
        @NotBlank(message = "country is required") String country,
        String phone,
        @Email(message = "email must be valid") String email,
        @Size(max = 4000) String description,
        @Size(max = 1024) String imageUrl,
        @Min(1) @Max(5) Integer starRating,
        @NotNull(message = "propertyType is required") PropertyType propertyType,
        List<String> amenities
) {}
