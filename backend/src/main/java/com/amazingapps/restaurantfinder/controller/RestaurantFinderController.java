package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import com.amazingapps.restaurantfinder.domain.RestaurantType;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantCreateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantSummaryResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateRequest;
import com.amazingapps.restaurantfinder.service.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/restaurants")
@AllArgsConstructor
public class RestaurantFinderController {
    private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<RestaurantSummaryResponse>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponse> getRestaurantById(@PathVariable String id) {
        return new ResponseEntity<>(restaurantService.findById(id),  HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantResponse> createRestaurant(@RequestBody RestaurantCreateRequest restaurant) {
        return new ResponseEntity<>(restaurantService.createRestaurant(restaurant),  HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<RestaurantResponse> updateRestaurant(@PathVariable String id,
                                                       @RequestBody RestaurantUpdateRequest restaurantDetails) {
            return ResponseEntity.ok(restaurantService.updateRestaurant(id, restaurantDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<Restaurant>> getFavorites() {
        //return restaurantService.getFavorites();
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String name) {
        //return restaurantService.searchRestaurants(name);
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<RestaurantSummaryResponse>> getRestaurantsByCuisine(@PathVariable RestaurantType type) {
        return ResponseEntity.ok(restaurantService.findByType(type));
    }

    @GetMapping("/rating/{minRating}")
    public ResponseEntity<List<RestaurantSummaryResponse>> getRestaurantsByMinRating(@PathVariable Double minRating) {
        return ResponseEntity.ok(restaurantService.findByMinRating(minRating));
    }
}
