package com.company.simpleservice.dto.request.Hotel;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UpdateHotelRequest {

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 255)
    private String name;

    @NotBlank(message = "address is required")
    private String address;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "country is required")
    private String country;

    private String phone;

    @Email(message = "email must be valid")
    private String email;

    @Min(value = 1) @Max(value = 5)
    private Integer starRating;
}
