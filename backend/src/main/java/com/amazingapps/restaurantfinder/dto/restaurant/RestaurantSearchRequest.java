package com.amazingapps.restaurantfinder.dto.restaurant;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;

/**
 * DTO for restaurant search request with filters.
 */
public record RestaurantSearchRequest(
        String name,
        String type,
        @DecimalMin(value = "0.0", message = "Minimum rating must be at least 0.0")
        @DecimalMax(value = "5.0", message = "Minimum rating must be at most 5.0")
        Double minRating,
        @DecimalMin(value = "-180.0", message = "Longitude must be between -180 and 180")
        @DecimalMax(value = "180.0", message = "Longitude must be between -180 and 180")
        Double longitude,
        @DecimalMin(value = "-90.0", message = "Latitude must be between -90 and 90")
        @DecimalMax(value = "90.0", message = "Latitude must be between -90 and 90")
        Double latitude,
        @Min(value = 1, message = "Search radius must be at least 1 meter")
        Double radiusInMeters
) {}
