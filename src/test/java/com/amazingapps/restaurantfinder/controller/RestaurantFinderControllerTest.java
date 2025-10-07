package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantFinderControllerTest {

    Restaurant.OpeningHours openingHours1;
    Restaurant.OpeningHours openingHours2;
    Restaurant.OpeningHours openingHours3;

    Restaurant.Review review1;
    Restaurant.Review review2;
    Restaurant.Review review3;
    Restaurant.Review review4;
    Restaurant.Review review5;
    Restaurant.Review review6;

    Restaurant restaurant1;
    Restaurant restaurant2;
    Restaurant restaurant3;

    @BeforeEach
    void setUp() {
        openingHours1 = new Restaurant.OpeningHours(
                true,
                Arrays.asList("Montag: 10:00 - 22:00", "Dienstag: 10:00 - 22:00",
                        "Mittwoch: 10:00 - 22:00", "Donnerstag: 10:00 - 22:00",
                        "Freitag: 10:00 - 24:00", "Samstag: 10:00 - 24:00",
                        "Sonntag: Geschlossen")
        );

        openingHours2 = new Restaurant.OpeningHours(
                true,
                Arrays.asList("Montag: 11:00 - 21:00", "Dienstag: 11:00 - 21:00",
                        "Mittwoch: 11:00 - 21:00", "Donnerstag: 11:00 - 21:00",
                        "Freitag: 11:00 - 23:00", "Samstag: 11:00 - 23:00",
                        "Sonntag: 11:00 - 21:00")
        );

        openingHours3 = new Restaurant.OpeningHours(
                true,
                Arrays.asList("Montag: 12:00 - 22:00", "Dienstag: 12:00 - 22:00",
                        "Mittwoch: 12:00 - 22:00", "Donnerstag: 12:00 - 22:00",
                        "Freitag: 12:00 - 24:00", "Samstag: 12:00 - 24:00",
                        "Sonntag: 12:00 - 22:00")
        );

        review1 = new Restaurant.Review("Max Mustermann", 5.0, "Sehr gute Pizza!",
                LocalDateTime.now().minusDays(1), LocalDateTime.now());
        review2 = new Restaurant.Review("Erika Mustermann", 4.0, "Leckere Pasta.",
                        LocalDateTime.now().minusDays(2), LocalDateTime.now());
        review3 = new Restaurant.Review("Anna Beispiel", 4.5, "Echt lecker!",
                LocalDateTime.now().minusDays(3), LocalDateTime.now());
        review4 = new Restaurant.Review("Thomas Beispiel", 3.5, "Könnte besser sein.",
                        LocalDateTime.now().minusDays(1), LocalDateTime.now());
        review5 = new Restaurant.Review("Julia Sushi", 5.0, "Fantastisches Sushi!",
                LocalDateTime.now().minusHours(5), LocalDateTime.now());
        review6 = new Restaurant.Review("Martin Sushi", 4.0, "Gute Auswahl.",
                        LocalDateTime.now().minusDays(2), LocalDateTime.now()) ;

        // Beispiel Restaurant 1
        restaurant1 = new Restaurant();
        restaurant1.setName("Tasty Bites");
        restaurant1.setRating(4.5);
        restaurant1.setPriceLevel(2);
        restaurant1.setPhoneNumber("+49 123 456 789");
        restaurant1.setFormattedAddress("Musterstraße 1, 12345 Musterstadt");
        restaurant1.setOpeningHours(openingHours1);
        restaurant1.setLocation(new Point(13.4050, 52.5200));    // Koordinaten für Berlin
        restaurant1.setReviews(Arrays.asList(review1, review2));

        // Beispiel Restaurant 2
        restaurant2 = new Restaurant();
        restaurant2.setName("Spicy Thai");
        restaurant2.setRating(4.0);
        restaurant2.setPriceLevel(1);
        restaurant2.setPhoneNumber("+49 987 654 321");
        restaurant2.setFormattedAddress("Beispielweg 2, 54321 Beispielstadt");
        restaurant2.setOpeningHours(openingHours2);
        restaurant2.setLocation(new Point(13.4051, 52.5201));    // Koordinaten für einen anderen Ort
        restaurant2.setReviews(Arrays.asList(review3, review4));


        // Beispiel Restaurant 3
        restaurant3 = new Restaurant();
        restaurant3.setName("Sushi Paradise");
        restaurant3.setRating(4.8);
        restaurant3.setPriceLevel(3);
        restaurant3.setPhoneNumber("+49 111 222 333");
        restaurant3.setFormattedAddress("Fischstraße 3, 45678 Fischstadt");
        restaurant3.setOpeningHours(openingHours3);
        restaurant3.setLocation(new Point(13.4052, 52.5202));    // Koordinaten für einen anderen Ort
        restaurant3.setReviews(Arrays.asList(review5, review6));
    }

    @Test
    void getAllRestaurants() {
    }

    @Test
    void getRestaurantById() {
    }

    @Test
    void createRestaurant() {
    }

    @Test
    void updateRestaurant() {
    }

    @Test
    void deleteRestaurant() {
    }

    @Test
    void getFavorites() {
    }

    @Test
    void searchRestaurants() {
    }

    @Test
    void getRestaurantsByCuisine() {
    }

    @Test
    void getRestaurantsByMinRating() {
    }
}