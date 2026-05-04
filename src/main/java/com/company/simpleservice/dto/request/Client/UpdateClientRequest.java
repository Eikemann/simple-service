package com.company.simpleservice.dto.request.Client;

import com.company.simpleservice.models.ClientStatus;
import com.company.simpleservice.models.Gender;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateClientRequest {

    @Size(min = 2, max = 255, message = "fullName must be between 2 and 255 characters")
    private String fullName;

    private Gender gender;

    private ClientStatus status;
}
