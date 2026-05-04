package com.company.simpleservice.dto.response;


import com.company.simpleservice.models.ClientStatus;
import com.company.simpleservice.models.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ClientResponse {

    private Long id;
    private String fullName;
    private Gender gender;
    private ClientStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
