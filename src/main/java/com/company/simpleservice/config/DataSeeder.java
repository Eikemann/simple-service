package com.company.simpleservice.config;

import com.company.simpleservice.models.*;
import com.company.simpleservice.repository.AmenityRepository;
import com.company.simpleservice.repository.PropertyRepository;
import com.company.simpleservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Seeds sample properties (with amenities and rooms) on first startup so the
 * Android app has real data to browse. Idempotent: does nothing once any
 * property exists. Safe to leave enabled in dev.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final AmenityRepository amenityRepository;

    private final Map<String, Amenity> amenities = new HashMap<>();

    @Override
    @Transactional
    public void run(String... args) {
        if (propertyRepository.count() > 0) {
            log.info("DataSeeder: properties already present, skipping seed.");
            return;
        }
        log.info("DataSeeder: empty database, seeding sample properties...");

        cacheAmenities("WiFi", "Pool", "Spa", "Free Parking", "Restaurant",
                "Gym", "Bar", "Beach Access", "Air Conditioning", "Breakfast");

        seed("Grand Palace Hotel", "12 Rue de Rivoli", "Paris", "France", PropertyType.HOTEL, 5,
                "A magnificent palace hotel in the heart of Paris with breathtaking views of the Eiffel Tower.",
                new BigDecimal("343"), "WiFi", "Spa", "Restaurant", "Bar", "Air Conditioning");

        seed("Sea View Resort", "880 Pacific Coast Hwy", "Malibu", "USA", PropertyType.VILLA, 5,
                "Perched on the cliffs of Malibu with direct beach access and panoramic Pacific Ocean views.",
                new BigDecimal("412"), "WiFi", "Pool", "Beach Access", "Restaurant", "Breakfast");

        seed("Mountain Inn", "45 Rockies Trail", "Aspen", "USA", PropertyType.HOUSE, 4,
                "A cozy mountain retreat in the Rocky Mountains, perfect for skiers and hikers.",
                new BigDecimal("299"), "WiFi", "Free Parking", "Restaurant", "Breakfast");

        seed("City Central Hotel", "200 Oxford Street", "London", "United Kingdom", PropertyType.HOTEL, 4,
                "Ideally located in the heart of London, steps from major attractions and shopping.",
                new BigDecimal("255"), "WiFi", "Bar", "Gym", "Air Conditioning");

        seed("The Royal Garden", "1-1 Chiyoda", "Tokyo", "Japan", PropertyType.HOTEL, 5,
                "An exquisite blend of traditional Japanese aesthetics and contemporary luxury with a zen garden.",
                new BigDecimal("410"), "WiFi", "Spa", "Restaurant", "Gym", "Breakfast");

        seed("Riverside Lodge", "7 Limmatquai", "Zurich", "Switzerland", PropertyType.APARTMENT, 4,
                "A refined lodge along the banks of the Limmat River, combining Swiss precision with warm hospitality.",
                new BigDecimal("330"), "WiFi", "Free Parking", "Spa", "Breakfast");

        seed("Desert Oasis Hotel", "Al Qudra Road", "Dubai", "United Arab Emirates", PropertyType.HOTEL, 5,
                "A spectacular oasis in the Dubai desert with private pools, luxury tents, and stargazing.",
                new BigDecimal("460"), "WiFi", "Pool", "Spa", "Restaurant", "Bar", "Air Conditioning");

        seed("Ocean Breeze Resort", "Jl. Pantai Kuta", "Bali", "Indonesia", PropertyType.VILLA, 4,
                "A serene Balinese resort surrounded by tropical gardens and rice terraces with an infinity pool.",
                new BigDecimal("340"), "WiFi", "Pool", "Beach Access", "Spa", "Restaurant");

        log.info("DataSeeder: seeded {} properties.", propertyRepository.count());
    }

    private void cacheAmenities(String... names) {
        for (String name : names) {
            Amenity amenity = amenityRepository.findByNameIgnoreCase(name)
                    .orElseGet(() -> amenityRepository.save(Amenity.builder().name(name).build()));
            amenities.put(name, amenity);
        }
    }

    private void seed(String name, String address, String city, String country,
                      PropertyType type, int stars, String description,
                      BigDecimal basePrice, String... amenityNames) {
        Set<Amenity> propertyAmenities = new LinkedHashSet<>();
        for (String a : amenityNames) {
            propertyAmenities.add(amenities.get(a));
        }

        Property property = Property.builder()
                .name(name)
                .address(address)
                .city(city)
                .country(country)
                .phone("+1-555-0100")
                .email("reservations@" + slug(name) + ".example.com")
                .description(description)
                .imageUrl("https://picsum.photos/seed/" + slug(name) + "/800/600")
                .starRating(stars)
                .propertyType(type)
                .amenities(propertyAmenities)
                .build();
        propertyRepository.save(property);

        // Two room tiers; the cheaper one drives the property's "from" price.
        roomRepository.save(Room.builder()
                .property(property).roomNumber("101").roomType(RoomType.DOUBLE)
                .capacity(2).pricePerNight(basePrice).status(RoomStatus.AVAILABLE).build());
        roomRepository.save(Room.builder()
                .property(property).roomNumber("201").roomType(RoomType.SUITE)
                .capacity(4).pricePerNight(basePrice.multiply(new BigDecimal("1.6")))
                .status(RoomStatus.AVAILABLE).build());
    }

    private String slug(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("(^-|-$)", "");
    }
}
