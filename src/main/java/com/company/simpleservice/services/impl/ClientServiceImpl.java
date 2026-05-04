package com.company.simpleservice.services.impl;

import com.company.simpleservice.dto.request.Client.CreateClientRequest;
import com.company.simpleservice.dto.request.Client.UpdateClientRequest;
import com.company.simpleservice.dto.response.ClientResponse;
import com.company.simpleservice.exceptions.SubjectNotFoundException;
import com.company.simpleservice.mapper.ClientMapper;
import com.company.simpleservice.models.Client;
import com.company.simpleservice.models.ClientStatus;
import com.company.simpleservice.repository.ClientRepository;
import com.company.simpleservice.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public ClientResponse create(CreateClientRequest request) {
        log.debug("Creating client: {}", request.getFullName());

        Client client = Client.builder()
                .fullName(request.getFullName())
                .gender(request.getGender())
                .clientStatus(ClientStatus.ACTIVE)
                .build();

        Client saved = clientRepository.save(client);
        log.info("Client created with id={}", saved.getId());
        return clientMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientResponse> findAll() {
        log.debug("Fetching all clients");
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ClientResponse findById(Long id) {
        log.debug("Fetching client id={}", id);
        Client client = getClientOrThrow(id);
        return clientMapper.toResponse(client);
    }

    @Override
    @Transactional
    public ClientResponse update(Long id, UpdateClientRequest request) {
        log.debug("Updating client id={}", id);
        Client client = getClientOrThrow(id);

        if (request.getFullName() != null) {
            client.setFullName(request.getFullName());
        }
        if (request.getGender() != null) {
            client.setGender(request.getGender());
        }
        if (request.getStatus() != null) {
            client.setClientStatus(request.getStatus());
        }

        Client updated = clientRepository.save(client);
        log.info("Client id={} updated", updated.getId());
        return clientMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.debug("Deleting client id={}", id);
        Client client = getClientOrThrow(id);
        clientRepository.delete(client);
        log.info("Client id={} deleted", id);
    }

    private Client getClientOrThrow(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException(id));
    }
}
