package com.company.simpleservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String email;
    private Integer starRating;
    private LocalDateTime createdAt;
}
