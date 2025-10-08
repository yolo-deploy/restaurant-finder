package com.amazingapps.restaurantfinder.dto.restaurant;

import java.util.List;

/**
 * DTO for simplified restaurant information used in lists and search results.
 */
public record RestaurantSummaryResponse(
        String id,
        String name,
        Double rating,
        Integer priceLevel,
        List<String> types,
        String formattedAddress,
        LocationResponse location,
        Boolean openNow
) {}
