package com.company.simpleservice.repository;

import com.company.simpleservice.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByPropertyId(Long hotelId);

    @Query("select avg(r.rating) from Review r where r.property.id = :propertyId")
    Double findAverageRatingByPropertyId(@Param("propertyId") Long propertyId);

    long countByPropertyId(Long propertyId);
}
