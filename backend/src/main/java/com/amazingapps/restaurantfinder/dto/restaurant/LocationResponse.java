package com.amazingapps.restaurantfinder.dto.restaurant;

/**
 * DTO for restaurant location containing longitude and latitude.
 */
public record LocationResponse(
        Double longitude,
        Double latitude
) {}
