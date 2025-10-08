package com.amazingapps.restaurantfinder.dto.restaurant;

import com.amazingapps.restaurantfinder.domain.RestaurantType;
import com.amazingapps.restaurantfinder.domain.WeekDay;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

/**
 * Represents a request for creating a restaurant.
 */
public record RestaurantCreateRequest(
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
        String formattedAddress,

        @NotNull(message = "Latitude cannot be null")
        Double latitude,

        @NotNull(message = "Longitude cannot be null")
        Double longitude,

        @NotNull(message = "Open now cannot be null")
        Boolean openNow,

        @NotEmpty(message = "Weekdays cannot be empty")
        List<WeekDay> weekdays
) {}