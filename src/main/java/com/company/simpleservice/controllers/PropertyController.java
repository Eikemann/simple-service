package com.company.simpleservice.controllers;

import com.company.simpleservice.dto.request.Property.CreatePropertyRequest;
import com.company.simpleservice.dto.request.Property.UpdatePropertyRequest;
import com.company.simpleservice.dto.response.PropertyResponse;
import com.company.simpleservice.models.PropertyType;
import com.company.simpleservice.services.PropertyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
@Tag(name = "Property", description = "Property Managing")
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public void create(@Valid @RequestBody CreatePropertyRequest request) {
        propertyService.create(request);
    }

    @GetMapping
    public List<PropertyResponse> findAll() {
        return propertyService.findAll();
    }

    @GetMapping("/search")
    public List<PropertyResponse> search(@RequestParam(required = false) String city,
                                         @RequestParam(required = false) PropertyType type) {
        return propertyService.search(city, type);
    }

    @GetMapping("/{id}")
    public PropertyResponse findById(@PathVariable long id) {
        return propertyService.findById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody UpdatePropertyRequest request) {
        propertyService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        propertyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
