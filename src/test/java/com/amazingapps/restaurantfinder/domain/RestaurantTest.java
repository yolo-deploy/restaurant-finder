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
        assertNull(restaurant.getOpeningHours());
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
        List<String> types = Arrays.asList("Italian", "Pizza", "Fine Dining");

        restaurant.setTypes(types);

        assertEquals(types, restaurant.getTypes());
        assertEquals(3, restaurant.getTypes().size());
        assertTrue(restaurant.getTypes().contains("Italian"));
    }

    @Test
    void setRatingCount_ShouldSetRatingCountCorrectly() {
        Restaurant restaurant = new Restaurant();
        Integer ratingCount = 150;

        restaurant.setRatingCount(ratingCount);

        assertEquals(ratingCount, restaurant.getRatingCount());
    }

    @Test
    void setFormattedAddress_ShouldSetFormattedAddressCorrectly() {
        Restaurant restaurant = new Restaurant();
        String address = "123 Main St, Berlin, Germany";

        restaurant.setFormattedAddress(address);

        assertEquals(address, restaurant.getFormattedAddress());
    }

    @Test
    void setLocation_ShouldSetLocationCorrectly() {
        Restaurant restaurant = new Restaurant();
        Point location = new Point(13.4050, 52.5200); // Berlin coordinates

        restaurant.setLocation(location);

        assertEquals(location, restaurant.getLocation());
        assertEquals(13.4050, restaurant.getLocation().getX());
        assertEquals(52.5200, restaurant.getLocation().getY());
    }

    @Test
    void setOpeningHours_ShouldSetOpeningHoursCorrectly() {
        Restaurant restaurant = new Restaurant();
        Restaurant.OpeningHours openingHours = new Restaurant.OpeningHours();
        openingHours.setOpenNow(true);

        restaurant.setOpeningHours(openingHours);

        assertEquals(openingHours, restaurant.getOpeningHours());
        assertTrue(restaurant.getOpeningHours().getOpenNow());
    }

    @Test
    void setReviews_ShouldSetReviewsCorrectly() {
        Restaurant restaurant = new Restaurant();
        Restaurant.Review review1 = new Restaurant.Review();
        review1.setAuthorName("John Doe");
        review1.setRating(5.0);
        review1.setText("Great food!");

        Restaurant.Review review2 = new Restaurant.Review();
        review2.setAuthorName("Jane Smith");
        review2.setRating(4.0);
        review2.setText("Good service");

        List<Restaurant.Review> reviews = Arrays.asList(review1, review2);
        restaurant.setReviews(reviews);

        assertEquals(reviews, restaurant.getReviews());
        assertEquals(2, restaurant.getReviews().size());
        assertEquals("John Doe", restaurant.getReviews().get(0).getAuthorName());
        assertEquals(5.0, restaurant.getReviews().get(0).getRating());
    }

    @Test
    void fullRestaurantObject_ShouldWorkCorrectly() {
        Restaurant restaurant = new Restaurant();
        String id = "restaurant123";
        String name = "Test Restaurant";
        Double rating = 4.5;
        Integer priceLevel = 3;
        String phoneNumber = "+49 123 456789";
        List<String> types = Arrays.asList("Italian", "Pizza");
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
    void openingHours_ShouldCreateAndWorkCorrectly() {
        Restaurant.OpeningHours openingHours = new Restaurant.OpeningHours();

        assertNull(openingHours.getOpenNow());
        assertNull(openingHours.getWeekdayText());
    }

    @Test
    void openingHours_SetOpenNow_ShouldWorkCorrectly() {
        Restaurant.OpeningHours openingHours = new Restaurant.OpeningHours();

        openingHours.setOpenNow(true);
        assertTrue(openingHours.getOpenNow());

        openingHours.setOpenNow(false);
        assertFalse(openingHours.getOpenNow());
    }

    @Test
    void openingHours_SetWeekdayText_ShouldWorkCorrectly() {
        Restaurant.OpeningHours openingHours = new Restaurant.OpeningHours();
        List<String> weekdayText = Arrays.asList(
            "Monday: 9:00 AM – 10:00 PM",
            "Tuesday: 9:00 AM – 10:00 PM",
            "Wednesday: 9:00 AM – 10:00 PM"
        );

        openingHours.setWeekdayText(weekdayText);

        assertEquals(weekdayText, openingHours.getWeekdayText());
        assertEquals(3, openingHours.getWeekdayText().size());
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
    void review_SetAuthorName_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        String authorName = "John Doe";

        review.setAuthorName(authorName);

        assertEquals(authorName, review.getAuthorName());
    }

    @Test
    void review_SetRating_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        Double rating = 4.5;

        review.setRating(rating);

        assertEquals(rating, review.getRating());
    }

    @Test
    void review_SetText_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        String text = "Great restaurant with excellent food!";

        review.setText(text);

        assertEquals(text, review.getText());
    }

    @Test
    void review_SetRelativeTimeDescription_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        LocalDateTime relativeTime = LocalDateTime.now();

        review.setRelativeTimeDescription(relativeTime);

        assertEquals(relativeTime, review.getRelativeTimeDescription());
    }

    @Test
    void review_SetCreatedAt_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        LocalDateTime createdAt = LocalDateTime.now();

        review.setCreatedAt(createdAt);

        assertEquals(createdAt, review.getCreatedAt());
    }

    @Test
    void review_FullObject_ShouldWorkCorrectly() {
        Restaurant.Review review = new Restaurant.Review();
        String authorName = "John Doe";
        Double rating = 5.0;
        String text = "Amazing food and service!";
        LocalDateTime relativeTime = LocalDateTime.now().minusDays(2);
        LocalDateTime createdAt = LocalDateTime.now();

        review.setAuthorName(authorName);
        review.setRating(rating);
        review.setText(text);
        review.setRelativeTimeDescription(relativeTime);
        review.setCreatedAt(createdAt);

        assertEquals(authorName, review.getAuthorName());
        assertEquals(rating, review.getRating());
        assertEquals(text, review.getText());
        assertEquals(relativeTime, review.getRelativeTimeDescription());
        assertEquals(createdAt, review.getCreatedAt());
    }
}
