package com.amazingapps.restaurantfinder.dto.restaurant;

import com.amazingapps.restaurantfinder.domain.RestaurantType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

/**
 * Represents a request for an update a restaurant.
 */
public record RestaurantUpdateRequest(
        @NotBlank(message = "Name cannot be blank")
        String Name,

        @PositiveOrZero
        @NotNull(message = "Price level cannot be null")
        Integer priceLevel,

        @NotBlank(message = "Phone number cannot be blank")
        String phoneNumber,

        @NotEmpty(message = "Types cannot be empty")
        List<RestaurantType> types,

        @NotBlank(message = "Formatted address cannot be blank")
        String formattedAddress
) {}