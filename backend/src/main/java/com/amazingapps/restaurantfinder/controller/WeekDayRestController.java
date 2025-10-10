package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.WeekDay;
import com.amazingapps.restaurantfinder.security.AuthInterceptor;
import com.amazingapps.restaurantfinder.security.AuthRequired;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/api/v1/weekdays")
@AuthRequired(AuthInterceptor.class)
@Tag(name = "weekdays", description = "Weekday type endpoints")
public class WeekDayRestController {

    /**
     * Retrieves all available weekdays.
     *
     * @return ResponseEntity containing an array of WeekDay objects
     */
    @GetMapping
    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Get all weekdays")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All weekdays"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
            @ApiResponse(responseCode = "503", description = "Service unavailable"),
    })
    public ResponseEntity<WeekDay[]> getAll() {
        return ResponseEntity.ok(WeekDay.values());
    }
}