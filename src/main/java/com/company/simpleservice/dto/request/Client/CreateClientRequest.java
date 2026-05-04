package com.company.simpleservice.dto.request.Client;

import com.company.simpleservice.models.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateClientRequest {

    @NotBlank(message = "fullName is required")
    @Size(min = 2, max = 255, message = "fullName must be between 2 and 255 characters")
    private String fullName;

    @NotNull(message = "gender is required")
    private Gender gender;
}
