package com.company.simpleservice.repository;

import com.company.simpleservice.models.Property;
import com.company.simpleservice.models.PropertyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByCityIgnoreCase(String city);
    List<Property> findByPropertyType(PropertyType type);
    List<Property> findByCityIgnoreCaseAndPropertyType(String city, PropertyType type);
}
