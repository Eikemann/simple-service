package com.company.simpleservice.mapper;

import com.company.simpleservice.dto.response.ClientResponse;
import com.company.simpleservice.models.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public ClientResponse toResponse(Client client) {
        return new ClientResponse(
                client.getId(),
                client.getFullName(),
                client.getGender(),
                client.getClientStatus(),
                client.getCreatedAt(),
                client.getUpdatedAt()
        );
    }
}
