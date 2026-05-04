package com.company.simpleservice.services;


import com.company.simpleservice.dto.request.Client.CreateClientRequest;
import com.company.simpleservice.dto.request.Client.UpdateClientRequest;
import com.company.simpleservice.dto.response.ClientResponse;

import java.util.List;

public interface ClientService {

    ClientResponse create(CreateClientRequest request);

    List<ClientResponse> findAll();

    ClientResponse findById(Long id);

    ClientResponse update(Long id, UpdateClientRequest request);

    void delete(Long id);
}
