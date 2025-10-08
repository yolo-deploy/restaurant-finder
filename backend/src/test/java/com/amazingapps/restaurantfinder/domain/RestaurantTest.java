package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Restaurant domain entity and its inner classes.
 */
class RestaurantTest {

    @Test
    void constructor_ShouldCreateEmptyRestaurant() {
        Restaurant restaurant = new Restaurant();

        assertNull(restaurant.getId());
        assertNull(restaurant.getName());
        assertNull(restaurant.getRating());
        assertNull(restaurant.getPriceLevel());
        assertNull(restaurant.getPhoneNumber());
        assertNull(restaurant.getTypes());
        assertNull(restaurant.getRatingCount());
        assertNull(restaurant.getReviews());
        assertNull(restaurant.getFormattedAddress());
        assertNull(restaurant.getOpeningDays());
        assertNull(restaurant.getLocation());
    }

    @Test
    void setName_ShouldSetNameCorrectly() {
        Restaurant restaurant = new Restaurant();
        String name = "Test Restaurant";

        restaurant.setName(name);

        assertEquals(name, restaurant.getName());
    }

    @Test
    void setRating_ShouldSetRatingCorrectly() {
        Restaurant restaurant = new Restaurant();
        Double rating = 4.5;

        restaurant.setRating(rating);

        assertEquals(rating, restaurant.getRating());
    }

    @Test
    void setPriceLevel_ShouldSetPriceLevelCorrectly() {
        Restaurant restaurant = new Restaurant();
        Integer priceLevel = 3;

        restaurant.setPriceLevel(priceLevel);

        assertEquals(priceLevel, restaurant.getPriceLevel());
    }

    @Test
    void setPhoneNumber_ShouldSetPhoneNumberCorrectly() {
        Restaurant restaurant = new Restaurant();
        String phoneNumber = "+49 123 456789";

        restaurant.setPhoneNumber(phoneNumber);

        assertEquals(phoneNumber, restaurant.getPhoneNumber());
    }

    @Test
    void setTypes_ShouldSetTypesCorrectly() {
        Restaurant restaurant = new Restaurant();
        List<RestaurantType> types = Arrays.asList(RestaurantType.ITALIAN, RestaurantType.GERMAN);

        restaurant.setTypes(types);

        assertEquals(types, restaurant.getTypes());
        assertEquals(2, restaurant.getTypes().size());
    }

    @Test
    void setRatingCount_ShouldSetRatingCountCorrectly() {
        Restaurant restaurant = new Restaurant();
        Integer ratingCount = 100;

        restaurant.setRatingCount(ratingCount);

        assertEquals(ratingCount, restaurant.getRatingCount());
    }

    @Test
    void setFormattedAddress_ShouldSetFormattedAddressCorrectly() {
        Restaurant restaurant = new Restaurant();
        String address = "123 Main St, Berlin";

        restaurant.setFormattedAddress(address);

        assertEquals(address, restaurant.getFormattedAddress());
    }

    @Test
    void setLocation_ShouldSetLocationCorrectly() {
        Restaurant restaurant = new Restaurant();
        Point location = new Point(13.4050, 52.5200);

        restaurant.setLocation(location);

        assertEquals(location, restaurant.getLocation());
    }

    @Test
    void setOpeningDays_ShouldSetOpeningDaysCorrectly() {
        Restaurant restaurant = new Restaurant();
        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();

        restaurant.setOpeningDays(openingDays);

        assertEquals(openingDays, restaurant.getOpeningDays());
    }

    @Test
    void review_ShouldCreateAndWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();

        assertNull(review.getAuthorName());
        assertNull(review.getRating());
        assertNull(review.getText());
        assertNull(review.getRelativeTimeDescription());
        assertNull(review.getCreatedAt());
    }

    @Test
    void review_SetValues_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        String authorName = "John Doe";
        Double rating = 5.0;
        String text = "Great food!";
        LocalDateTime now = LocalDateTime.now();

        review.setAuthorName(authorName);
        review.setRating(rating);
        review.setText(text);
        review.setRelativeTimeDescription(now);
        review.setCreatedAt(now);

        assertEquals(authorName, review.getAuthorName());
        assertEquals(rating, review.getRating());
        assertEquals(text, review.getText());
        assertEquals(now, review.getRelativeTimeDescription());
        assertEquals(now, review.getCreatedAt());
    }

    @Test
    void setReviews_ShouldSetReviewsCorrectly() {
        Restaurant restaurant = new Restaurant();
        Restaurant.Review review1 = new Restaurant.Review();
        review1.setAuthorName("John");
        review1.setRating(5.0);

        Restaurant.Review review2 = new Restaurant.Review();
        review2.setAuthorName("Jane");
        review2.setRating(4.0);

        List<Restaurant.Review> reviews = Arrays.asList(review1, review2);

        restaurant.setReviews(reviews);

        assertEquals(reviews, restaurant.getReviews());
        assertEquals(2, restaurant.getReviews().size());
    }

    @Test
    void fullRestaurantObject_ShouldWorkCorrectly() {
        Restaurant restaurant = new Restaurant();
        String id = "restaurant123";
        String name = "Test Restaurant";
        Double rating = 4.5;
        Integer priceLevel = 3;
        String phoneNumber = "+49 123 456789";

        List<RestaurantType> types = Arrays.asList(RestaurantType.ITALIAN, RestaurantType.GERMAN);

        Integer ratingCount = 100;
        String address = "123 Main St, Berlin";
        Point location = new Point(13.4050, 52.5200);
        LocalDateTime now = LocalDateTime.now();

        restaurant.setId(id);
        restaurant.setName(name);
        restaurant.setRating(rating);
        restaurant.setPriceLevel(priceLevel);
        restaurant.setPhoneNumber(phoneNumber);
        restaurant.setTypes(types);
        restaurant.setRatingCount(ratingCount);
        restaurant.setFormattedAddress(address);
        restaurant.setLocation(location);
        restaurant.setCreationDate(now);
        restaurant.setModifyDate(now);

        assertEquals(id, restaurant.getId());
        assertEquals(name, restaurant.getName());
        assertEquals(rating, restaurant.getRating());
        assertEquals(priceLevel, restaurant.getPriceLevel());
        assertEquals(phoneNumber, restaurant.getPhoneNumber());
        assertEquals(types, restaurant.getTypes());
        assertEquals(ratingCount, restaurant.getRatingCount());
        assertEquals(address, restaurant.getFormattedAddress());
        assertEquals(location, restaurant.getLocation());
        assertEquals(now, restaurant.getCreationDate());
        assertEquals(now, restaurant.getModifyDate());
    }

    @Test
    void openingDays_ShouldCreateAndWorkCorrectly() {
        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();

        assertNull(openingDays.getOpenNow());
        assertNull(openingDays.getWeekdays());
    }

    @Test
    void openingDays_SetOpenNow_ShouldWorkCorrectly() {
        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();

        openingDays.setOpenNow(true);
        assertTrue(openingDays.getOpenNow());

        openingDays.setOpenNow(false);
        assertFalse(openingDays.getOpenNow());
    }

    @Test
    void openingDays_SetWeekdays_ShouldWorkCorrectly() {
        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();
        List<WeekDay> weekdays = Arrays.asList(WeekDay.MONDAY, WeekDay.TUESDAY);

        openingDays.setWeekdays(weekdays);

        assertEquals(weekdays, openingDays.getWeekdays());
        assertEquals(2, openingDays.getWeekdays().size());
    }
}