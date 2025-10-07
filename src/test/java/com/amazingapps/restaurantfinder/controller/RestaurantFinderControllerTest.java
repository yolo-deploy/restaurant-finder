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


        restaurant1 = new Restaurant(
                "Tasty Bites",
                4.5,
                2,
                "+49 123 456 789",
                Arrays.asList("Italienisch", "Pizza", "Familienrestaurant"),
                150,
                Arrays.asList(
                        new Restaurant.Review("Max Mustermann", 5.0, "Sehr gute Pizza!",
                                LocalDateTime.now().minusDays(1), LocalDateTime.now()),
                        new Restaurant.Review("Erika Mustermann", 4.0, "Leckere Pasta.",
                                LocalDateTime.now().minusDays(2), LocalDateTime.now())
                ),
                "Musterstraße 1, 12345 Musterstadt",
                openingHours1,
                new Point(13.4050, 52.5200) // Koordinaten für Berlin
        );

        // Beispiel Restaurant 2
        restaurant2 = new Restaurant(
                "Spicy Thai",
                4.0,
                1,
                "+49 987 654 321",
                Arrays.asList("Thailändisch", "Street Food"),
                80,
                Arrays.asList(
                        new Restaurant.Review("Anna Beispiel", 4.5, "Echt lecker!",
                                LocalDateTime.now().minusDays(3), LocalDateTime.now()),
                        new Restaurant.Review("Thomas Beispiel", 3.5, "Könnte besser sein.",
                                LocalDateTime.now().minusDays(1), LocalDateTime.now())
                ),
                "Beispielweg 2, 54321 Beispielstadt",
                openingHours2,
                new Point(13.4051, 52.5201) // Koordinaten für einen anderen Ort
        );

        // Beispiel Restaurant 3
        restaurant3 = new Restaurant(
                "Sushi Paradise",
                4.8,
                3,
                "+49 111 222 333",
                Arrays.asList("Japanisch", "Sushi", "All-you-can-eat"),
                200,
                Arrays.asList(
                        new Restaurant.Review("Julia Sushi", 5.0, "Fantastisches Sushi!",
                                LocalDateTime.now().minusHours(5), LocalDateTime.now()),
                        new Restaurant.Review("Martin Sushi", 4.0, "Gute Auswahl.",
                                LocalDateTime.now().minusDays(2), LocalDateTime.now())
                ),
                "Fischstraße 3, 45678 Fischstadt",
                openingHours3,
                new Point(13.4052, 52.5202) // Koordinaten für einen weiteren Ort
        );

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