package com.company.simpleservice.controllers;

import com.company.simpleservice.dto.request.Order.CreateOrderRequest;
import com.company.simpleservice.dto.request.Order.UpdateOrderRequest;
import com.company.simpleservice.dto.response.OrderResponse;
import com.company.simpleservice.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order Managing")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void create(@Valid @RequestBody CreateOrderRequest request) {
        orderService.create(request);
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable long id) {
        return orderService.findById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<OrderResponse> findByHotelId(@PathVariable long hotelId) {
        return orderService.findByHotelId(hotelId);
    }

    @GetMapping("/room/{roomId}")
    public List<OrderResponse> findByRoomId(@PathVariable long roomId) {
        return orderService.findByRoomId(roomId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody UpdateOrderRequest request) {
        orderService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
