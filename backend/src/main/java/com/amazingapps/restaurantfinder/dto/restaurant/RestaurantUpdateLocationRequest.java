package com.amazingapps.restaurantfinder.dto.restaurant;

import jakarta.validation.constraints.NotNull;

/**
 * Represents a request for updating a restaurant's location.
 */
public record RestaurantUpdateLocationRequest(
        @NotNull(message = "Latitude cannot be null")
        Double latitude,

        @NotNull(message = "Longitude cannot be null")
        Double longitude
) {}