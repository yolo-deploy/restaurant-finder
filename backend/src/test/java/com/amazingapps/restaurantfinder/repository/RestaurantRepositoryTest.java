package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import com.amazingapps.restaurantfinder.domain.RestaurantType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ActiveProfiles("test")
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;

    @BeforeEach
    void setUp() {
        restaurantRepository.deleteAll();

        restaurant1 = new Restaurant();
        restaurant1.setName("Pizza Palace");
        restaurant1.setTypes(List.of(RestaurantType.ITALIAN));
        restaurant1.setRating(4.5);
        restaurant1.setLocation(new Point(8.0, 50.0));

        restaurant2 = new Restaurant();
        restaurant2.setName("Burger House");
        restaurant2.setTypes(List.of(RestaurantType.AMERICAN));
        restaurant2.setRating(3.8);
        restaurant2.setLocation(new Point(8.1, 50.1));

        restaurant3 = new Restaurant();
        restaurant3.setName("Sushi Express");
        restaurant3.setTypes(List.of(RestaurantType.ASIAN));
        restaurant3.setRating(4.8);
        restaurant3.setLocation(new Point(9.0, 51.0));

        restaurantRepository.saveAll(List.of(restaurant1, restaurant2, restaurant3));
    }

    @Test
    void findByNameContainingIgnoreCase_ShouldFindMatchingRestaurants() {
        List<Restaurant> results = restaurantRepository.findByNameContainingIgnoreCase("pizza");

        assertEquals(1, results.size());
        assertEquals("Pizza Palace", results.get(0).getName());
    }

    @Test
    void findByNameContainingIgnoreCase_CaseInsensitive_ShouldWork() {
        List<Restaurant> results = restaurantRepository.findByNameContainingIgnoreCase("BURGER");

        assertEquals(1, results.size());
        assertEquals("Burger House", results.get(0).getName());
    }

    @Test
    void findByNameContainingIgnoreCase_PartialMatch_ShouldWork() {
        List<Restaurant> results = restaurantRepository.findByNameContainingIgnoreCase("House");

        assertEquals(1, results.size());
        assertEquals("Burger House", results.get(0).getName());
    }

    @Test
    void findByNameContainingIgnoreCase_NoMatch_ShouldReturnEmpty() {
        List<Restaurant> results = restaurantRepository.findByNameContainingIgnoreCase("NonExistent");

        assertTrue(results.isEmpty());
    }

    @Test
    void findAllByTypesContainingIgnoreCase_ShouldFindByType() {
        List<Restaurant> results = restaurantRepository.findAllByTypesContainingIgnoreCase("ITALIAN");

        assertEquals(1, results.size());
        assertEquals("Pizza Palace", results.get(0).getName());
    }

    @Test
    void findAllByTypesContainingIgnoreCase_CaseInsensitive_ShouldWork() {
        List<Restaurant> results = restaurantRepository.findAllByTypesContainingIgnoreCase("american");

        assertEquals(1, results.size());
        assertEquals("Burger House", results.get(0).getName());
    }

    @Test
    void findByRatingGreaterThanEqual_ShouldFindHighRatedRestaurants() {
        List<Restaurant> results = restaurantRepository.findByRatingGreaterThanEqual(4.0);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Pizza Palace")));
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Sushi Express")));
    }

    @Test
    void findByRatingGreaterThanEqual_WithHighThreshold_ShouldFindOnlyBest() {
        List<Restaurant> results = restaurantRepository.findByRatingGreaterThanEqual(4.7);

        assertEquals(1, results.size());
        assertEquals("Sushi Express", results.get(0).getName());
    }

    @Test
    @Disabled("Requires geo index in MongoDB which is not available in test environment")
    void findByLocationNear_ShouldFindNearbyRestaurants() {
        Point searchPoint = new Point(8.05, 50.05);
        Distance distance = new Distance(20, Metrics.KILOMETERS);

        List<Restaurant> results = restaurantRepository.findByLocationNear(searchPoint, distance);

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Pizza Palace")));
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Burger House")));
    }

    @Test
    @Disabled("Requires geo index in MongoDB which is not available in test environment")
    void findByLocationNear_WithSmallRadius_ShouldFindFewer() {
        Point searchPoint = new Point(8.0, 50.0);
        Distance distance = new Distance(1, Metrics.KILOMETERS);

        List<Restaurant> results = restaurantRepository.findByLocationNear(searchPoint, distance);

        assertTrue(results.size() >= 1);
        assertTrue(results.stream().anyMatch(r -> r.getName().equals("Pizza Palace")));
    }

    @Test
    @Disabled("Requires geo index in MongoDB which is not available in test environment")
    void findByLocationNear_FarLocation_ShouldReturnEmpty() {
        Point farPoint = new Point(0.0, 0.0);
        Distance distance = new Distance(100);

        List<Restaurant> results = restaurantRepository.findByLocationNear(farPoint, distance);

        assertTrue(results.isEmpty());
    }

    @Test
    void saveAndFindById_ShouldWork() {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName("Test Restaurant");
        newRestaurant.setTypes(List.of(RestaurantType.OTHER));

        Restaurant saved = restaurantRepository.save(newRestaurant);
        Restaurant found = restaurantRepository.findById(saved.getId()).orElse(null);

        assertNotNull(saved.getId());
        assertNotNull(found);
        assertEquals("Test Restaurant", found.getName());
    }

    @Test
    void deleteById_ShouldRemoveRestaurant() {
        String restaurantId = restaurant1.getId();

        restaurantRepository.deleteById(restaurantId);
        boolean exists = restaurantRepository.existsById(restaurantId);

        assertFalse(exists);
    }

    @Test
    void findAll_ShouldReturnAllRestaurants() {
        List<Restaurant> all = restaurantRepository.findAll();

        assertEquals(3, all.size());
    }

    @Test
    void count_ShouldReturnCorrectCount() {
        long count = restaurantRepository.count();

        assertEquals(3, count);
    }
}