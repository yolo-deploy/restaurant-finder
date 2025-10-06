package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
@AllArgsConstructor
public class RestaurantFinderController {
    //private RestaurantService restaurantService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        //return restaurantService.getAllRestaurants();
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        //Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        //return restaurant.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        Restaurant restaurant = new Restaurant();
        return new ResponseEntity<>(restaurant,  HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        //return restaurantService.createRestaurant(restaurant);
        return new ResponseEntity<>(restaurant,  HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id,
                                                       @RequestBody Restaurant restaurantDetails) {
        try {
            //Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurantDetails);
            Restaurant updatedRestaurant = new Restaurant();
            return ResponseEntity.ok(updatedRestaurant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        //restaurantService.deleteRestaurant(id);
        return ResponseEntity.ok().build();
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

    @GetMapping("/cuisine/{cuisine}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByCuisine(@PathVariable String cuisine) {
        //return restaurantService.getRestaurantsByCuisine(cuisine);
        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/rating/{minRating}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByMinRating(@PathVariable Integer minRating) {
        //return restaurantService.getRestaurantsByMinRating(minRating);
        return ResponseEntity.ok(List.of());
    }
}
