package com.amazingapps.restaurantfinder.mapper;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import com.amazingapps.restaurantfinder.domain.RestaurantType;
import com.amazingapps.restaurantfinder.domain.WeekDay;
import com.amazingapps.restaurantfinder.dto.restaurant.LocationResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.OpeningDaysResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantCreateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantSummaryResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateLocationRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateOpeningDaysRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.ReviewCreateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.ReviewResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RestaurantMapperTest {

    private final RestaurantMapper restaurantMapper = Mappers.getMapper(RestaurantMapper.class);

    @Test
    void toEntity_FromCreateRequest_ShouldMapCorrectly() {
        RestaurantCreateRequest request = new RestaurantCreateRequest(
                "Test Restaurant",
                1,
                "+1234567890",
                List.of(RestaurantType.ITALIAN),
                "123 Test Street",
                50.0,
                8.0,
                true,
                List.of(WeekDay.MONDAY, WeekDay.TUESDAY)
        );

        Restaurant result = restaurantMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("Test Restaurant", result.getName());
        assertEquals(List.of(RestaurantType.ITALIAN), result.getTypes());
        assertEquals(1, result.getPriceLevel());
        assertEquals("+1234567890", result.getPhoneNumber());
        assertNotNull(result.getLocation());
        assertEquals(8.0, result.getLocation().getX());
        assertEquals(50.0, result.getLocation().getY());
        assertNotNull(result.getOpeningDays());
        assertTrue(result.getOpeningDays().getOpenNow());
        assertEquals(2, result.getOpeningDays().getWeekdays().size());
    }

    @Test
    void toEntity_FromUpdateRequest_ShouldMapCorrectly() {
        RestaurantUpdateRequest request = new RestaurantUpdateRequest(
                "Updated Restaurant",
                2,
                "+1234567890",
                List.of(RestaurantType.ASIAN),
                "456 Updated Street"
        );

        Restaurant result = restaurantMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("Updated Restaurant", result.getName());
        assertEquals(List.of(RestaurantType.ASIAN), result.getTypes());
        assertNull(result.getId());
        assertNull(result.getLocation());
        assertNull(result.getOpeningDays());
    }

    @Test
    void updateEntity_ShouldUpdateExistingRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("test-id");
        restaurant.setName("Old Name");
        restaurant.setLocation(new Point(10.0, 20.0));

        RestaurantUpdateRequest request = new RestaurantUpdateRequest(
                "New Name",
                3,
                "+9876543210",
                List.of(RestaurantType.GERMAN),
                "789 New Street"
        );

        restaurantMapper.updateEntity(restaurant, request);

        assertEquals("test-id", restaurant.getId());
        assertEquals("New Name", restaurant.getName());
        assertEquals(List.of(RestaurantType.GERMAN), restaurant.getTypes());
        assertNotNull(restaurant.getLocation());
    }

    @Test
    void updateLocation_ShouldUpdateLocationOnly() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("test-id");
        restaurant.setName("Test Restaurant");
        restaurant.setLocation(new Point(10.0, 20.0));

        RestaurantUpdateLocationRequest request = new RestaurantUpdateLocationRequest(60.0, 15.0);

        restaurantMapper.updateLocation(restaurant, request);

        assertEquals("test-id", restaurant.getId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertNotNull(restaurant.getLocation());
        assertEquals(15.0, restaurant.getLocation().getX());
        assertEquals(60.0, restaurant.getLocation().getY());
    }

    @Test
    void updateOpeningDays_ShouldUpdateOpeningDaysOnly() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("test-id");
        restaurant.setName("Test Restaurant");

        RestaurantUpdateOpeningDaysRequest request = new RestaurantUpdateOpeningDaysRequest(
                false,
                List.of(WeekDay.FRIDAY, WeekDay.SATURDAY, WeekDay.SUNDAY)
        );

        restaurantMapper.updateOpeningDays(restaurant, request);

        assertEquals("test-id", restaurant.getId());
        assertEquals("Test Restaurant", restaurant.getName());
        assertNotNull(restaurant.getOpeningDays());
        assertFalse(restaurant.getOpeningDays().getOpenNow());
        assertEquals(3, restaurant.getOpeningDays().getWeekdays().size());
    }

    @Test
    void toSummaryResponse_ShouldMapCorrectly() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("test-id");
        restaurant.setName("Test Restaurant");
        restaurant.setTypes(List.of(RestaurantType.ITALIAN, RestaurantType.AMERICAN));
        restaurant.setRating(4.5);
        restaurant.setPriceLevel(2);
        restaurant.setFormattedAddress("123 Test Street");
        restaurant.setLocation(new Point(8.0, 50.0));

        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();
        openingDays.setOpenNow(true);
        restaurant.setOpeningDays(openingDays);

        RestaurantSummaryResponse result = restaurantMapper.toSummaryResponse(restaurant);

        assertNotNull(result);
        assertEquals("test-id", result.id());
        assertEquals("Test Restaurant", result.name());
        assertEquals(2, result.types().size());
        assertEquals("ITALIAN", result.types().get(0));
        assertEquals("AMERICAN", result.types().get(1));
        assertEquals(4.5, result.rating());
        assertEquals(2, result.priceLevel());
        assertEquals("123 Test Street", result.formattedAddress());
        assertNotNull(result.location());
        assertEquals(8.0, result.location().latitude());
        assertEquals(50.0, result.location().longitude());
        assertTrue(result.openNow());
    }

    @Test
    void toReviewEntity_ShouldMapCorrectly() {
        ReviewCreateRequest request = new ReviewCreateRequest(
                "John Doe",
                4.8,
                "Great food!",
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now()
        );

        Restaurant.Review result = restaurantMapper.toReviewEntity(request);

        assertNotNull(result);
        assertEquals("John Doe", result.getAuthorName());
        assertEquals(4.8, result.getRating());
        assertEquals("Great food!", result.getText());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void toLocationResponse_ShouldMapCorrectly() {
        Restaurant restaurant = new Restaurant();
        restaurant.setLocation(new Point(8.5, 50.1));

        LocationResponse result = restaurantMapper.toLocationResponse(restaurant);
        
        assertNotNull(result);
        assertEquals(50.1, result.latitude());
        assertEquals(8.5, result.longitude());
    }

    @Test
    void toLocationResponse_WithNullLocation_ShouldReturnLocationWithNulls() {
        Restaurant restaurant = new Restaurant();
        restaurant.setLocation(null);

        LocationResponse result = restaurantMapper.toLocationResponse(restaurant);

        assertNotNull(result);
        assertNull(result.latitude());
        assertNull(result.longitude());
    }

    @Test
    void toOpeningDaysResponse_ShouldMapCorrectly() {
        Restaurant restaurant = new Restaurant();
        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();
        openingDays.setOpenNow(true);
        openingDays.setWeekdays(List.of(WeekDay.MONDAY, WeekDay.FRIDAY));
        restaurant.setOpeningDays(openingDays);

        OpeningDaysResponse result = restaurantMapper.toOpeningDaysResponse(restaurant);

        assertNotNull(result);
        assertTrue(result.openNow());
        assertEquals(2, result.weekDays().size());
        assertTrue(result.weekDays().contains(WeekDay.MONDAY));
        assertTrue(result.weekDays().contains(WeekDay.FRIDAY));
    }

    @Test
    void createPoint_WithValidCoordinates_ShouldCreatePoint() {
        Point result = restaurantMapper.createPoint(50.0, 8.0);

        assertNotNull(result);
        assertEquals(8.0, result.getX());
        assertEquals(50.0, result.getY());
    }

    @Test
    void createPoint_WithNullCoordinates_ShouldReturnNull() {
        Point result1 = restaurantMapper.createPoint(null, 8.0);
        Point result2 = restaurantMapper.createPoint(50.0, null);
        Point result3 = restaurantMapper.createPoint(null, null);

        assertNull(result1);
        assertNull(result2);
        assertNull(result3);
    }

    @Test
    void createOpeningDays_WithValidData_ShouldCreateOpeningDays() {
        List<WeekDay> weekdays = List.of(WeekDay.MONDAY, WeekDay.TUESDAY);

        Restaurant.OpeningDays result = restaurantMapper.createOpeningDays(true, weekdays);

        assertNotNull(result);
        assertTrue(result.getOpenNow());
        assertEquals(2, result.getWeekdays().size());
    }

    @Test
    void createOpeningDays_WithNullData_ShouldReturnNull() {
        Restaurant.OpeningDays result = restaurantMapper.createOpeningDays(null, null);

        assertNull(result);
    }

    @Test
    void convertTypesToStrings_ShouldConvertCorrectly() {
        List<RestaurantType> types = List.of(RestaurantType.ITALIAN, RestaurantType.ASIAN);

        List<String> result = restaurantMapper.convertTypesToStrings(types);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("ITALIAN", result.get(0));
        assertEquals("ASIAN", result.get(1));
    }

    @Test
    void convertTypesToStrings_WithNullInput_ShouldReturnNull() {
        List<String> result = restaurantMapper.convertTypesToStrings(null);

        assertNull(result);
    }

    @Test
    void convertPointToLocationResponse_ShouldConvertCorrectly() {
        Point point = new Point(8.0, 50.0);

        LocationResponse result = restaurantMapper.convertPointToLocationResponse(point);
        
        assertNotNull(result);
        assertEquals(8.0, result.latitude());
        assertEquals(50.0, result.longitude());
    }

    @Test
    void convertPointToLocationResponse_WithNullPoint_ShouldReturnNull() {
        LocationResponse result = restaurantMapper.convertPointToLocationResponse(null);
        
        assertNull(result);
    }

    @Test
    void toResponse_ShouldMapCorrectly() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId("test-id");
        restaurant.setName("Test Restaurant");
        restaurant.setRating(4.5);
        restaurant.setPriceLevel(3);
        restaurant.setPhoneNumber("+49 123 456789");
        restaurant.setTypes(List.of(RestaurantType.ITALIAN, RestaurantType.AMERICAN));
        restaurant.setRatingCount(10);
        restaurant.setFormattedAddress("123 Test Street");
        restaurant.setLocation(new Point(8.0, 50.0));
        restaurant.setCreationDate(LocalDateTime.now());
        restaurant.setModifyDate(LocalDateTime.now());

        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();
        openingDays.setOpenNow(true);
        openingDays.setWeekdays(List.of(WeekDay.MONDAY, WeekDay.FRIDAY));
        restaurant.setOpeningDays(openingDays);

        Restaurant.Review review = new Restaurant.Review();
        review.setAuthorName("John Doe");
        review.setRating(5.0);
        review.setText("Great food!");
        review.setRelativeTimeDescription(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());
        restaurant.setReviews(List.of(review));

        RestaurantResponse result = restaurantMapper.toResponse(restaurant);

        assertNotNull(result);
        assertEquals("test-id", result.id());
        assertEquals("Test Restaurant", result.name());
        assertEquals(4.5, result.rating());
        assertEquals(3, result.priceLevel());
        assertEquals("+49 123 456789", result.phoneNumber());
        assertEquals(2, result.types().size());
        assertEquals("ITALIAN", result.types().get(0));
        assertEquals("AMERICAN", result.types().get(1));
        assertEquals(10, result.ratingCount());
        assertEquals("123 Test Street", result.formattedAddress());

        assertNotNull(result.location());
        assertEquals(1, result.reviews().size());

        if (result.openingDays() != null) {
            assertTrue(result.openingDays().openNow());
        }
    }

    @Test
    void toSummaryResponseList_ShouldMapListCorrectly() {
        Restaurant restaurant1 = new Restaurant();
        restaurant1.setId("test-id-1");
        restaurant1.setName("Test Restaurant 1");
        restaurant1.setTypes(List.of(RestaurantType.ITALIAN));
        restaurant1.setRating(4.5);
        restaurant1.setPriceLevel(3);
        restaurant1.setFormattedAddress("123 Test Street");
        restaurant1.setLocation(new Point(8.0, 50.0));

        Restaurant.OpeningDays openingDays1 = new Restaurant.OpeningDays();
        openingDays1.setOpenNow(true);
        restaurant1.setOpeningDays(openingDays1);

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setId("test-id-2");
        restaurant2.setName("Test Restaurant 2");
        restaurant2.setTypes(List.of(RestaurantType.AMERICAN));
        restaurant2.setRating(3.5);
        restaurant2.setPriceLevel(2);
        restaurant2.setFormattedAddress("456 Test Avenue");
        restaurant2.setLocation(new Point(9.0, 51.0));

        Restaurant.OpeningDays openingDays2 = new Restaurant.OpeningDays();
        openingDays2.setOpenNow(false);
        restaurant2.setOpeningDays(openingDays2);

        List<Restaurant> restaurants = List.of(restaurant1, restaurant2);

        List<RestaurantSummaryResponse> result = restaurantMapper.toSummaryResponseList(restaurants);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("test-id-1", result.get(0).id());
        assertEquals("Test Restaurant 1", result.get(0).name());
        assertEquals("test-id-2", result.get(1).id());
        assertEquals("Test Restaurant 2", result.get(1).name());
    }

    @Test
    void toReviewResponseList_ShouldMapListCorrectly() {
        Restaurant.Review review1 = new Restaurant.Review();
        review1.setAuthorName("John Doe");
        review1.setRating(5.0);
        review1.setText("Great food!");
        review1.setRelativeTimeDescription(LocalDateTime.now());

        Restaurant.Review review2 = new Restaurant.Review();
        review2.setAuthorName("Jane Smith");
        review2.setRating(4.0);
        review2.setText("Good service!");
        review2.setRelativeTimeDescription(LocalDateTime.now().minusDays(1));

        List<Restaurant.Review> reviews = List.of(review1, review2);

        List<ReviewResponse> result = restaurantMapper.toReviewResponseList(reviews);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).authorName());
        assertEquals(5.0, result.get(0).rating());
        assertEquals("Great food!", result.get(0).text());
        assertEquals("Jane Smith", result.get(1).authorName());
        assertEquals(4.0, result.get(1).rating());
        assertEquals("Good service!", result.get(1).text());
    }

    @Test
    void createOpeningDays_WithValidWeekdays_ShouldCreateCorrectly() {
        List<WeekDay> weekdays = List.of(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY);

        Restaurant.OpeningDays result = restaurantMapper.createOpeningDays(true, weekdays);

        assertNotNull(result);
        assertTrue(result.getOpenNow());
        assertEquals(3, result.getWeekdays().size());
        assertTrue(result.getWeekdays().contains(WeekDay.MONDAY));
        assertTrue(result.getWeekdays().contains(WeekDay.TUESDAY));
        assertTrue(result.getWeekdays().contains(WeekDay.WEDNESDAY));
    }

    @Test
    void createOpeningDays_WithEmptyWeekdays_ShouldCreateCorrectly() {
        List<WeekDay> weekdays = List.of();

        Restaurant.OpeningDays result = restaurantMapper.createOpeningDays(false, weekdays);

        assertNotNull(result);
        assertFalse(result.getOpenNow());
        assertEquals(0, result.getWeekdays().size());
    }

    @Test
    void convertTypesToStrings_WithEmptyList_ShouldReturnEmptyList() {
        List<RestaurantType> types = List.of();

        List<String> result = restaurantMapper.convertTypesToStrings(types);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void createPoint_WithZeroCoordinates_ShouldCreatePoint() {
        Point result = restaurantMapper.createPoint(0.0, 0.0);

        assertNotNull(result);
        assertEquals(0.0, result.getX());
        assertEquals(0.0, result.getY());
    }

    @Test
    void createPoint_WithNegativeCoordinates_ShouldCreatePoint() {
        Point result = restaurantMapper.createPoint(-50.0, -8.0);

        assertNotNull(result);
        assertEquals(-8.0, result.getX());
        assertEquals(-50.0, result.getY());
    }

    @Test
    void toOpeningDaysResponse_WithNullOpeningDays_ShouldReturnResponseWithNulls() {
        Restaurant restaurant = new Restaurant();
        restaurant.setOpeningDays(null);

        OpeningDaysResponse result = restaurantMapper.toOpeningDaysResponse(restaurant);

        assertNotNull(result);
        assertNull(result.openNow());
        assertNull(result.weekDays());
    }

    @Test
    void toEntity_FromUpdateRequest_WithAllFields_ShouldMapCorrectly() {
        RestaurantUpdateRequest request = new RestaurantUpdateRequest(
                "Updated Restaurant",
                4,
                "+1234567890",
                List.of(RestaurantType.ASIAN, RestaurantType.INDIAN),
                "789 Updated Street"
        );

        Restaurant result = restaurantMapper.toEntity(request);

        assertNotNull(result);
        assertEquals("Updated Restaurant", result.getName());
        assertEquals(4, result.getPriceLevel());
        assertEquals("+1234567890", result.getPhoneNumber());
        assertEquals(2, result.getTypes().size());
        assertTrue(result.getTypes().contains(RestaurantType.ASIAN));
        assertTrue(result.getTypes().contains(RestaurantType.INDIAN));
        assertEquals("789 Updated Street", result.getFormattedAddress());
    }
}

