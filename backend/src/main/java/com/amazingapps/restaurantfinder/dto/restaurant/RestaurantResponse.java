package com.amazingapps.restaurantfinder.dto.restaurant;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for restaurant response containing all restaurant information.
 */
public record RestaurantResponse(
        String id,
        String name,
        Double rating,
        Integer priceLevel,
        String phoneNumber,
        List<String> types,
        Integer ratingCount,
        List<ReviewResponse> reviews,
        String formattedAddress,
        OpeningDaysResponse openingDays,
        LocationResponse location,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
