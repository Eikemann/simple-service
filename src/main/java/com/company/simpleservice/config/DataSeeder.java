package com.company.simpleservice.config;

import com.company.simpleservice.models.*;
import com.company.simpleservice.repository.AmenityRepository;
import com.company.simpleservice.repository.PropertyRepository;
import com.company.simpleservice.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Seeds properties (with amenities and rooms) on first startup so the Android app
 * has real data to browse. Data comes from {@code resources/seed/hotels.csv},
 * extracted once from the SerpApi Google Hotels API across the supported cities
 * (see seed-data/ at repo root).
 * Idempotent: does nothing once any property exists. Safe to leave enabled in dev.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private static final String CSV_PATH = "seed/hotels.csv";
    private static final BigDecimal SUITE_MULTIPLIER = new BigDecimal("1.6");

    private final PropertyRepository propertyRepository;
    private final RoomRepository roomRepository;
    private final AmenityRepository amenityRepository;

    private final Map<String, Amenity> amenityCache = new HashMap<>();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (propertyRepository.count() > 0) {
            log.info("DataSeeder: properties already present, skipping seed.");
            return;
        }
        log.info("DataSeeder: empty database, seeding properties from {}...", CSV_PATH);

        List<String[]> rows = readCsv();
        for (String[] row : rows) {
            seed(row);
        }

        log.info("DataSeeder: seeded {} properties.", propertyRepository.count());
    }

    /**
     * Columns: name, address, city, country, phone, email, star_rating,
     * property_type, price_per_night, image_url, amenities (pipe-separated), description.
     */
    private void seed(String[] c) {
        Set<Amenity> amenities = new LinkedHashSet<>();
        for (String name : c[10].split("\\|")) {
            String trimmed = name.trim();
            if (!trimmed.isEmpty()) {
                amenities.add(findOrCreateAmenity(trimmed));
            }
        }

        Property property = Property.builder()
                .name(c[0])
                .address(c[1])
                .city(c[2])
                .country(c[3])
                .phone(c[4])
                .email(c[5])
                .description(c[11])
                .imageUrl(c[9])
                .starRating(Integer.parseInt(c[6].trim()))
                .propertyType(PropertyType.valueOf(c[7].trim()))
                .amenities(amenities)
                .build();
        propertyRepository.save(property);

        // Two room tiers; the cheaper one drives the property's "from" price.
        BigDecimal basePrice = new BigDecimal(c[8].trim());
        roomRepository.save(Room.builder()
                .property(property).roomNumber("101").roomType(RoomType.DOUBLE)
                .capacity(2).pricePerNight(basePrice).status(RoomStatus.AVAILABLE).build());
        roomRepository.save(Room.builder()
                .property(property).roomNumber("201").roomType(RoomType.SUITE)
                .capacity(4)
                .pricePerNight(basePrice.multiply(SUITE_MULTIPLIER).setScale(0, RoundingMode.HALF_UP))
                .status(RoomStatus.AVAILABLE).build());
    }

    private Amenity findOrCreateAmenity(String name) {
        return amenityCache.computeIfAbsent(name, n ->
                amenityRepository.findByNameIgnoreCase(n)
                        .orElseGet(() -> amenityRepository.save(Amenity.builder().name(n).build())));
    }

    /** Minimal RFC-4180 CSV reader: handles quoted fields, escaped quotes, and a header row. */
    private List<String[]> readCsv() throws Exception {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ClassPathResource(CSV_PATH).getInputStream(), StandardCharsets.UTF_8))) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;
                rows.add(parseLine(line));
            }
        }
        return rows;
    }

    private String[] parseLine(String line) {
        List<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (inQuotes) {
                if (ch == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        field.append('"');
                        i++;
                    } else {
                        inQuotes = false;
                    }
                } else {
                    field.append(ch);
                }
            } else if (ch == '"') {
                inQuotes = true;
            } else if (ch == ',') {
                fields.add(field.toString());
                field.setLength(0);
            } else {
                field.append(ch);
            }
        }
        fields.add(field.toString());
        return fields.toArray(new String[0]);
    }
}
