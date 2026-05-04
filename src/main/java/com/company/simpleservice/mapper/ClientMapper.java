package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.ClientResponse;
import com.company.simpleservice.models.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientResponse toResponse(Client client) {
        return ClientResponse.builder()
                .id(client.getId())
                .fullName(client.getFullName())
                .gender(client.getGender())
                .status(client.getClientStatus())
                .createdAt(client.getCreatedAt())
                .updatedAt(client.getUpdatedAt())
                .build();
    }
}
