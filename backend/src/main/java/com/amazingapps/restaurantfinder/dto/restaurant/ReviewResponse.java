package com.amazingapps.restaurantfinder.dto.restaurant;

import java.time.LocalDateTime;

/**
 * DTO for restaurant review information.
 */
public record ReviewResponse(
        String authorName,
        Double rating,
        String text,
        LocalDateTime relativeTimeDescription,
        LocalDateTime createdAt
) {}