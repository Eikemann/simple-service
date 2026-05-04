package com.company.simpleservice.controllers;

import com.company.simpleservice.dto.request.Review.CreateReviewRequest;
import com.company.simpleservice.dto.request.Review.UpdateReviewRequest;
import com.company.simpleservice.dto.response.ReviewResponse;
import com.company.simpleservice.services.ReviewService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Review", description = "Review Managing")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public void create(@Valid @RequestBody CreateReviewRequest request) {
        reviewService.create(request);
    }

    @GetMapping
    public List<ReviewResponse> findAll() {
        return reviewService.findAll();
    }

    @GetMapping("/{id}")
    public ReviewResponse findById(@PathVariable long id) {
        return reviewService.findById(id);
    }

    @GetMapping("/hotel/{hotelId}")
    public List<ReviewResponse> findByHotelId(@PathVariable long hotelId) {
        return reviewService.findByHotelId(hotelId);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @Valid @RequestBody UpdateReviewRequest request) {
        reviewService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
