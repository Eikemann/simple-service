package com.company.simpleservice.controllers;

import com.company.simpleservice.dto.request.Client.CreateClientRequest;
import com.company.simpleservice.dto.request.Client.UpdateClientRequest;
import com.company.simpleservice.dto.response.ClientResponse;
import com.company.simpleservice.services.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    /**
     * POST /api/clients
     * Создание нового клиента. Статус по умолчанию — ACTIVE.
     */
    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request) {
        log.info("POST /api/clients — creating client '{}'", request.getFullName());
        ClientResponse response = clientService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * GET /api/clients
     * Получение списка всех клиентов.
     */
    @GetMapping
    public ResponseEntity<List<ClientResponse>> findAll() {
        log.info("GET /api/clients — fetching all clients");
        return ResponseEntity.ok(clientService.findAll());
    }

    /**
     * GET /api/clients/{id}
     * Получение клиента по ID. Возвращает 404, если не найден.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable Long id) {
        log.info("GET /api/clients/{} — fetching client", id);
        return ResponseEntity.ok(clientService.findById(id));
    }

    /**
     * PUT /api/clients/{id}
     * Обновление данных клиента. Возвращает 404, если не найден.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateClientRequest request) {
        log.info("PUT /api/clients/{} — updating client", id);
        return ResponseEntity.ok(clientService.update(id, request));
    }

    /**
     * DELETE /api/clients/{id}
     * Жёсткое удаление клиента. Возвращает 404, если не найден.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/clients/{} — deleting client", id);
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
