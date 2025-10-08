package com.amazingapps.restaurantfinder.service;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import com.amazingapps.restaurantfinder.domain.RestaurantType;
import com.amazingapps.restaurantfinder.domain.WeekDay;
import com.amazingapps.restaurantfinder.dto.restaurant.LocationResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.OpeningDaysResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantCreateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantSearchRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantSummaryResponse;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateLocationRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateOpeningDaysRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.RestaurantUpdateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.ReviewCreateRequest;
import com.amazingapps.restaurantfinder.dto.restaurant.ReviewResponse;
import com.amazingapps.restaurantfinder.mapper.RestaurantMapper;
import com.amazingapps.restaurantfinder.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository repository;

    @Mock
    private RestaurantMapper mapper;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant testRestaurant;
    private RestaurantCreateRequest createRequest;
    private RestaurantResponse restaurantResponse;

    @BeforeEach
    void setUp() {
        testRestaurant = new Restaurant();
        testRestaurant.setId("test-id");
        testRestaurant.setName("Test Restaurant");

        testRestaurant.setTypes(List.of(RestaurantType.ITALIAN));
        testRestaurant.setRating(4.5);
        testRestaurant.setRatingCount(10);

        createRequest = new RestaurantCreateRequest(
                "Test Restaurant",
                3,
                "+49 123 456789",
                List.of(RestaurantType.ITALIAN),
                "123 Main St, Berlin",
                52.5200,
                13.4050,
                true,
                List.of(WeekDay.MONDAY)
        );

        restaurantResponse = new RestaurantResponse(
                "test-id",
                "Test Restaurant",
                4.5,
                3,
                "+49 123 456789",
                List.of("ITALIAN"),
                10,
                List.of(),
                "123 Main St, Berlin",
                new OpeningDaysResponse(true, List.of()),
                new LocationResponse(52.5200, 13.4050),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void createRestaurant_ShouldCreateSuccessfully() {
        when(mapper.toEntity(createRequest)).thenReturn(testRestaurant);
        when(repository.save(testRestaurant)).thenReturn(testRestaurant);
        when(mapper.toResponse(testRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantService.createRestaurant(createRequest);

        assertNotNull(result);
        assertEquals("test-id", result.id());
        assertEquals("Test Restaurant", result.name());
        verify(repository).save(testRestaurant);
        verify(mapper).toEntity(createRequest);
        verify(mapper).toResponse(testRestaurant);
    }

    @Test
    void createRestaurant_WithNullTypes_ShouldThrowException() {
        RestaurantCreateRequest invalidRequest = new RestaurantCreateRequest(
                "Test Restaurant",
                3,
                "+49 123 456789",
                null,
                "123 Main St, Berlin",
                52.5200,
                13.4050,
                true,
                List.of(WeekDay.MONDAY)
        );

        assertThrows(IllegalArgumentException.class, () ->
            restaurantService.createRestaurant(invalidRequest));
    }

    @Test
    void createRestaurant_WithEmptyTypes_ShouldThrowException() {
        RestaurantCreateRequest invalidRequest = new RestaurantCreateRequest(
                "Test Restaurant",
                3,
                "+49 123 456789",
                List.of(),
                "123 Main St, Berlin",
                52.5200,
                13.4050,
                true,
                List.of(WeekDay.MONDAY)
        );

        assertThrows(IllegalArgumentException.class, () ->
            restaurantService.createRestaurant(invalidRequest));
    }

    @Test
    void findById_ShouldReturnRestaurant() {
        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(mapper.toResponse(testRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantService.findById("test-id");

        assertNotNull(result);
        assertEquals("test-id", result.id());
        verify(repository).getOrThrow("test-id");
        verify(mapper).toResponse(testRestaurant);
    }

    @Test
    void findAll_ShouldReturnAllRestaurants() {
        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );
        when(repository.findAll()).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test-id", result.get(0).id());
        verify(repository).findAll();
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void findByName_ShouldReturnMatchingRestaurants() {
        String searchName = "Test";
        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );
        when(repository.findByNameContainingIgnoreCase(searchName)).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.findByName(searchName);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByNameContainingIgnoreCase(searchName);
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void findByType_ShouldReturnMatchingRestaurants() {
        RestaurantType type = RestaurantType.ITALIAN;
        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );
        when(repository.findAllByTypesContainingIgnoreCase("ITALIAN")).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.findByType(type);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAllByTypesContainingIgnoreCase("ITALIAN");
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void findByMinRating_ShouldReturnMatchingRestaurants() {
        Double minRating = 4.0;
        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );
        when(repository.findByRatingGreaterThanEqual(minRating)).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.findByMinRating(minRating);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByRatingGreaterThanEqual(minRating);
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void addReview_ShouldAddReviewSuccessfully() {
        LocalDateTime now = LocalDateTime.now();
        ReviewCreateRequest reviewRequest = new ReviewCreateRequest(
                "John Doe",
                5.0,
                "Great food!",
                now,
                now
        );

        Restaurant.Review review = new Restaurant.Review();
        review.setAuthorName("John Doe");
        review.setRating(5.0);
        review.setText("Great food!");
        review.setRelativeTimeDescription(now);
        review.setCreatedAt(now);

        testRestaurant.setReviews(new ArrayList<>());

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(mapper.toReviewEntity(reviewRequest)).thenReturn(review);
        when(repository.save(any(Restaurant.class))).thenReturn(testRestaurant);
        when(mapper.toResponse(testRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantService.addReview("test-id", reviewRequest);

        assertNotNull(result);
        assertEquals(1, testRestaurant.getReviews().size());
        verify(repository).getOrThrow("test-id");
        verify(mapper).toReviewEntity(reviewRequest);
        verify(repository).save(testRestaurant);
    }

    @Test
    void addReview_WithInvalidRating_ShouldThrowException() {
        LocalDateTime now = LocalDateTime.now();
        ReviewCreateRequest invalidReviewRequest = new ReviewCreateRequest(
                "John Doe",
                6.0,
                "Bad rating",
                now,
                now
        );
        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);

        assertThrows(IllegalArgumentException.class, () ->
            restaurantService.addReview("test-id", invalidReviewRequest));
    }

    @Test
    void updateRestaurant_ShouldUpdateSuccessfully() {
        RestaurantUpdateRequest updateRequest = new RestaurantUpdateRequest(
                "Updated Restaurant",
                4,
                "+49 987 654321",
                List.of(RestaurantType.AMERICAN),
                "456 Updated St, Berlin"
        );

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(repository.save(testRestaurant)).thenReturn(testRestaurant);
        when(mapper.toResponse(testRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantService.updateRestaurant("test-id", updateRequest);

        assertNotNull(result);
        verify(repository).getOrThrow("test-id");
        verify(repository).save(testRestaurant);
    }

    @Test
    void deleteRestaurant_ShouldDeleteSuccessfully() {
        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);

        restaurantService.deleteRestaurant("test-id");

        verify(repository).getOrThrow("test-id");
        verify(repository).delete(testRestaurant);
    }

    @Test
    void search_WithLocationFilter_ShouldReturnFilteredResults() {
        RestaurantSearchRequest searchRequest = new RestaurantSearchRequest(
                null, null, null, 13.4050, 52.5200, 5.0
        );

        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );

        when(repository.findByLocationNear(any(Point.class), any(Distance.class))).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.search(searchRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByLocationNear(any(Point.class), any(Distance.class));
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void search_WithNameFilter_ShouldReturnFilteredResults() {
        RestaurantSearchRequest searchRequest = new RestaurantSearchRequest(
                "Pizza", null, null, null, null, null
        );

        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );

        when(repository.findByNameContainingIgnoreCase("Pizza")).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(any(List.class))).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.search(searchRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByNameContainingIgnoreCase("Pizza");
        verify(mapper).toSummaryResponseList(any(List.class));
    }

    @Test
    void search_WithTypeFilter_ShouldReturnFilteredResults() {
        RestaurantSearchRequest searchRequest = new RestaurantSearchRequest(
                null, "ITALIAN", null, null, null, null
        );

        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );

        when(repository.findAllByTypesContainingIgnoreCase("ITALIAN")).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.search(searchRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAllByTypesContainingIgnoreCase("ITALIAN");
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void search_WithMinRatingFilter_ShouldReturnFilteredResults() {
        RestaurantSearchRequest searchRequest = new RestaurantSearchRequest(
                null, null, 4.0, null, null, null
        );

        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );

        when(repository.findByRatingGreaterThanEqual(4.0)).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.search(searchRequest);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByRatingGreaterThanEqual(4.0);
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void findNearLocation_ShouldReturnNearbyRestaurants() {
        Double latitude = 52.5200;
        Double longitude = 13.4050;
        Double radiusKm = 5.0;

        List<Restaurant> restaurants = List.of(testRestaurant);
        List<RestaurantSummaryResponse> summaryResponses = List.of(
                new RestaurantSummaryResponse(
                        "test-id",
                        "Test Restaurant",
                        4.5,
                        3,
                        List.of("ITALIAN"),
                        "123 Main St, Berlin",
                        new LocationResponse(52.5200, 13.4050),
                        true
                )
        );

        when(repository.findByLocationNear(any(Point.class), any(Distance.class))).thenReturn(restaurants);
        when(mapper.toSummaryResponseList(restaurants)).thenReturn(summaryResponses);

        List<RestaurantSummaryResponse> result = restaurantService.findNearLocation(latitude, longitude, radiusKm);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByLocationNear(any(Point.class), any(Distance.class));
        verify(mapper).toSummaryResponseList(restaurants);
    }

    @Test
    void updateRestaurantLocation_ShouldUpdateLocationSuccessfully() {
        RestaurantUpdateLocationRequest locationRequest = new RestaurantUpdateLocationRequest(52.5300, 13.4100);

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(repository.save(testRestaurant)).thenReturn(testRestaurant);
        when(mapper.toResponse(testRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantService.updateRestaurantLocation("test-id", locationRequest);

        assertNotNull(result);
        verify(repository).getOrThrow("test-id");
        verify(mapper).updateLocation(testRestaurant, locationRequest);
        verify(repository).save(testRestaurant);
        verify(mapper).toResponse(testRestaurant);
    }

    @Test
    void updateRestaurantOpeningDays_ShouldUpdateOpeningDaysSuccessfully() {
        RestaurantUpdateOpeningDaysRequest openingDaysRequest = new RestaurantUpdateOpeningDaysRequest(
                false, List.of(WeekDay.MONDAY, WeekDay.TUESDAY)
        );

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(repository.save(testRestaurant)).thenReturn(testRestaurant);
        when(mapper.toResponse(testRestaurant)).thenReturn(restaurantResponse);

        RestaurantResponse result = restaurantService.updateRestaurantOpeningDays("test-id", openingDaysRequest);

        assertNotNull(result);
        verify(repository).getOrThrow("test-id");
        verify(mapper).updateOpeningDays(testRestaurant, openingDaysRequest);
        verify(repository).save(testRestaurant);
        verify(mapper).toResponse(testRestaurant);
    }

    @Test
    void getRestaurantReviews_ShouldReturnReviews() {
        Restaurant.Review review = new Restaurant.Review();
        review.setAuthorName("John Doe");
        review.setRating(5.0);
        review.setText("Great food!");
        testRestaurant.setReviews(List.of(review));

        ReviewResponse reviewResponse = new ReviewResponse("John Doe", 5.0, "Great food!", LocalDateTime.now(), LocalDateTime.now());

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(mapper.toReviewResponseList(testRestaurant.getReviews())).thenReturn(List.of(reviewResponse));

        List<ReviewResponse> result = restaurantService.getRestaurantReviews("test-id");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).authorName());
        verify(repository).getOrThrow("test-id");
        verify(mapper).toReviewResponseList(testRestaurant.getReviews());
    }

    @Test
    void getRestaurantLocation_ShouldReturnLocation() {
        LocationResponse locationResponse = new LocationResponse(52.5200, 13.4050);

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(mapper.toLocationResponse(testRestaurant)).thenReturn(locationResponse);

        LocationResponse result = restaurantService.getRestaurantLocation("test-id");

        assertNotNull(result);
        assertEquals(52.5200, result.longitude());
        assertEquals(13.4050, result.latitude());
        verify(repository).getOrThrow("test-id");
        verify(mapper).toLocationResponse(testRestaurant);
    }

    @Test
    void getRestaurantOpeningDays_ShouldReturnOpeningDays() {
        OpeningDaysResponse openingDaysResponse = new OpeningDaysResponse(true, List.of(WeekDay.MONDAY));

        when(repository.getOrThrow("test-id")).thenReturn(testRestaurant);
        when(mapper.toOpeningDaysResponse(testRestaurant)).thenReturn(openingDaysResponse);

        OpeningDaysResponse result = restaurantService.getRestaurantOpeningDays("test-id");

        assertNotNull(result);
        assertTrue(result.openNow());
        assertEquals(1, result.weekDays().size());
        verify(repository).getOrThrow("test-id");
        verify(mapper).toOpeningDaysResponse(testRestaurant);
    }
}