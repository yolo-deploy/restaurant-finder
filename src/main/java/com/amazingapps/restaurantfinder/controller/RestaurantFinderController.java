package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import com.amazingapps.restaurantfinder.repository.RestaurantRepository;
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

    // Repository für Datenzugriffe auf restaurants
    private final RestaurantRepository restaurantRepository;

    /**
     * Liefert alle restaurants zurück.
     */
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantRepository.findAll());
    }

    /**
     * Liefert ein restaurant anhand der ID zurück oder 404, wenn nicht gefunden.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable String id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        return restaurant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Erstellt ein neues restaurant.
     */
    @PostMapping("/create")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant saved = restaurantRepository.save(restaurant);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    /**
     * Aktualisiert ein restaurant. Falls nicht vorhanden, 404.
     */
    @PostMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id,
                                                       @RequestBody Restaurant restaurantDetails) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                    // ID beibehalten/setzen und speichern
                    restaurantDetails.setId(id);
                    Restaurant saved = restaurantRepository.save(restaurantDetails);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Löscht ein restaurant anhand der ID (idempotent).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        restaurantRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Beispiel-Endpunkt für favoriten (noch ohne Implementierung/Business-Logik).
     */
    @GetMapping("/favorites")
    public ResponseEntity<List<Restaurant>> getFavorites() {
        return ResponseEntity.ok(List.of());
    }

    /**
     * Sucht restaurants per Namensfragment (case-insensitive).
     */
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String name) {
        return ResponseEntity.ok(restaurantRepository.findByNameContainingIgnoreCase(name));
    }

    /**
     * Liefert restaurants nach Typ (z. B. "Italian").
     */
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByType(@PathVariable String type) {
        return ResponseEntity.ok(restaurantRepository.findByTypes(type));
    }

    /**
     * Liefert restaurants mit einer Mindestbewertung.
     */
    @GetMapping("/rating/{minRating}")
    public ResponseEntity<List<Restaurant>> getRestaurantsByMinRating(@PathVariable Double minRating) {
        return ResponseEntity.ok(restaurantRepository.findByRatingGreaterThanEqual(minRating));
    }
}
