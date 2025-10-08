package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.RestaurantType;
import com.amazingapps.restaurantfinder.security.AuthInterceptor;
import com.amazingapps.restaurantfinder.security.AuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing restaurant types.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/restaurant-types")
@AuthRequired(AuthInterceptor.class)
@Tag(name = "restaurant-types", description = "Restaurant type endpoints")
public class RestaurantTypeRestController {

    /**
     * Retrieves all available restaurant types.
     *
     * @return ResponseEntity containing an array of RestaurantType objects
     */
    @GetMapping
    @Operation(summary = "Get all restaurant types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All restaurant types"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "503", description = "Service unavailable"),
    })
    public ResponseEntity<RestaurantType[]> getAll() {
        return ResponseEntity.ok(RestaurantType.values());
    }
}