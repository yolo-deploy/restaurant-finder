package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.domain.RestaurantType;
import com.amazingapps.restaurantfinder.domain.WeekDay;
import com.amazingapps.restaurantfinder.dto.restaurant.*;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import com.amazingapps.restaurantfinder.exception.NotFoundObjectException;
import com.amazingapps.restaurantfinder.security.AuthInterceptor;
import com.amazingapps.restaurantfinder.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantFinderControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantFinderController controller;

    @Mock
    private AuthInterceptor authInterceptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        String userId = "user123";
        UserResponse response = new UserResponse("test@email.com");
        when(authInterceptor.getUserId()).thenReturn(userId);
    }

    @Test
    void getAllRestaurants_ShouldReturnRestaurantList() throws Exception {
        // Given
        RestaurantSummaryResponse summaryResponse = new RestaurantSummaryResponse(
                "1", "Test Restaurant", 4.5, 2,
                List.of("ITALIAN"), "Test Address",
                new LocationResponse(40.7128, -74.0060), true
        );

        // When & Then
        when(restaurantService.findAll()).thenReturn(Collections.singletonList(summaryResponse));
        ResponseEntity<List<RestaurantSummaryResponse>> result = controller.getAllRestaurants();
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(Collections.singletonList(summaryResponse), result.getBody());
    }

    @Test
    void getRestaurantById_ShouldReturnRestaurant() throws Exception {
        // Given
        RestaurantResponse response = new RestaurantResponse(
                "1", "Test Restaurant", 4.5, 2, "123-456-789",
                List.of("ITALIAN"), 100, List.of(),
                "Test Address",
                new OpeningDaysResponse(true, List.of(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY)),
                new LocationResponse(40.7128, -74.0060),
                LocalDateTime.now(), LocalDateTime.now()
        );

        // When & Then
        when(restaurantService.findById("1")).thenReturn(response);
        ResponseEntity<RestaurantResponse> result = controller.getRestaurantById("1");
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void createRestaurant_ShouldReturnCreatedRestaurant() throws Exception {
        // Given
        RestaurantCreateRequest createRequest = new RestaurantCreateRequest(
                "New Restaurant", 2, "123-456-789",
                List.of(RestaurantType.ITALIAN), "New Address",
                40.7128, -74.0060, true, List.of(WeekDay.MONDAY)
        );

        RestaurantResponse response = new RestaurantResponse(
                "1", "New Restaurant", 4.5, 2, "123-456-789",
                List.of("ITALIAN"), 100, List.of(),
                "New Address",
                new OpeningDaysResponse(true, List.of(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY)),
                new LocationResponse(40.7128, -74.0060),
                LocalDateTime.now(), LocalDateTime.now()
        );

        // When & Then
        when(restaurantService.createRestaurant(any(RestaurantCreateRequest.class))).thenReturn(response);
        ResponseEntity<RestaurantResponse> result = controller.createRestaurant(createRequest);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void updateRestaurant_ShouldReturnUpdatedRestaurant() throws Exception {
        // Given
        RestaurantUpdateRequest updateRequest = new RestaurantUpdateRequest(
                "Updated Restaurant", 3, "987-654-321",
                List.of(RestaurantType.ASIAN), "Updated Address"
        );

        RestaurantResponse response = new RestaurantResponse(
                "1", "Updated Restaurant", 4.5, 3, "987-654-321",
                List.of("ASIAN"), 100, List.of(),
                "Updated Address",
                new OpeningDaysResponse(true, List.of(WeekDay.MONDAY, WeekDay.TUESDAY, WeekDay.WEDNESDAY)),
                new LocationResponse(40.7128, -74.0060),
                LocalDateTime.now(), LocalDateTime.now()
        );

        // When & Then
        when(restaurantService.updateRestaurant(eq("1"), any(RestaurantUpdateRequest.class))).thenReturn(response);
        ResponseEntity<RestaurantResponse> result = controller.updateRestaurant("1", updateRequest);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deleteRestaurant_ShouldReturnOk() throws Exception {
        // Given

        // When & Then
        doNothing().when(restaurantService).deleteRestaurant("1");

        ResponseEntity<Void> result = controller.deleteRestaurant("1");
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void getRestaurantById_WithNonExistingId_ShouldThrowNotFoundObjectException() throws Exception {
        // Given
        // new NotFoundObjectException(getEntityName() + " not found with id: " + id));
        when(restaurantService.findById("999"))
                .thenThrow(new NotFoundObjectException("Not found with id: 999"));

        // When & Then
        assertThrows(NotFoundObjectException.class, () -> controller.getRestaurantById("999"));
    }

    @Test
    void updateRestaurant_WithNonExistingId_ShouldThrowNotFoundObjectException() throws Exception {
        // Given
        RestaurantUpdateRequest updateRequest = new RestaurantUpdateRequest(
                "Updated Restaurant", 3, "987-654-321",
                List.of(RestaurantType.ASIAN), "Updated Address"
        );

        // new NotFoundObjectException(getEntityName() + " not found with id: " + id));
        when(restaurantService.updateRestaurant(eq("999"), any(RestaurantUpdateRequest.class)))
                .thenThrow(new NotFoundObjectException("Not found with id: 999"));

        // When & Then
        assertThrows(NotFoundObjectException.class, () -> controller.updateRestaurant("999", updateRequest));
    }
}