package com.company.simpleservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity @Table(name = "properties")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(
            name = "property_amenities",
            joinColumns = @JoinColumn(name = "property_id")
    )
    @Column(name = "amenity")
    private List<String> amenities;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type", nullable = false)
    private PropertyType propertyType;

    @Column(name = "location_rating")
    private Double locationRating;

    @Column(name = "reviews_count")
    private Long reviewsCount;

    @Column(name = "rate_per_night", nullable = false)
    private BigDecimal ratePerNight;
}
