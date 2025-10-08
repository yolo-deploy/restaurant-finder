package com.amazingapps.restaurantfinder.service;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import com.amazingapps.restaurantfinder.domain.RestaurantType;
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
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Comprehensive service class for restaurant-related operations.
 * Handles all business logic and provides full CRUD functionality with proper DTO handling.
 */
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;
    private final RestaurantMapper mapper;

    /**
     * Creates a new restaurant from the provided request data.
     *
     * @param request the restaurant creation request
     * @return RestaurantResponse containing the created restaurant details
     */
    public RestaurantResponse createRestaurant(RestaurantCreateRequest request) {
        validateRestaurantTypes(request.types());
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    /**
     * Retrieves a restaurant by its ID.
     *
     * @param restaurantId the restaurant ID
     * @return RestaurantResponse containing the restaurant details
     */
    public RestaurantResponse findById(String restaurantId) {
        return mapper.toResponse(repository.getOrThrow(restaurantId));
    }

    /**
     * Finds all restaurants and returns summary information.
     *
     * @return List of RestaurantSummaryResponse containing basic restaurant info
     */
    public List<RestaurantSummaryResponse> findAll() {
        return mapper.toSummaryResponseList(repository.findAll());
    }

    /**
     * Searches restaurants by name (case-insensitive partial match).
     *
     * @param name the restaurant name to search for
     * @return List of RestaurantSummaryResponse matching the name criteria
     */
    public List<RestaurantSummaryResponse> findByName(String name) {
        return mapper.toSummaryResponseList(repository.findByNameContainingIgnoreCase(name.trim()));
    }

    /**
     * Searches restaurants by type/category.
     *
     * @param type the restaurant type to search for
     * @return List of RestaurantSummaryResponse matching the type criteria
     */
    public List<RestaurantSummaryResponse> findByType(RestaurantType type) {
        return mapper.toSummaryResponseList(repository.findAllByTypesContainingIgnoreCase(type.name()));
    }

    /**
     * Searches restaurants with minimum rating.
     *
     * @param minRating the minimum rating threshold
     * @return List of RestaurantSummaryResponse with rating >= minRating
     */
    public List<RestaurantSummaryResponse> findByMinRating(Double minRating) {
        return mapper.toSummaryResponseList(repository.findByRatingGreaterThanEqual(minRating));
    }

    /**
     * Searches restaurants near a location within specified radius.
     *
     * @param longitude the longitude of the search center
     * @param latitude  the latitude of the search center
     * @param radiusInMeters the search radius in meters
     * @return List of RestaurantSummaryResponse within the specified area
     */
    public List<RestaurantSummaryResponse> findNearLocation(Double longitude, Double latitude, Double radiusInMeters) {
        Point location = new Point(longitude, latitude);
        Distance distance = new Distance(radiusInMeters, Metrics.NEUTRAL);
        List<Restaurant> restaurants = repository.findByLocationNear(location, distance);
        return mapper.toSummaryResponseList(restaurants);
    }

    /**
     * Comprehensive search method that combines multiple filters.
     *
     * @param searchRequest the search criteria
     * @return List of RestaurantSummaryResponse matching the search criteria
     */
    public List<RestaurantSummaryResponse> search(RestaurantSearchRequest searchRequest) {
        List<Restaurant> restaurants;

        if (hasLocationSearch(searchRequest)) {
            restaurants = findRestaurantsNearLocation(searchRequest);
        } else if (hasNameSearch(searchRequest)) {
            restaurants = repository.findByNameContainingIgnoreCase(searchRequest.name().trim());
        } else if (hasTypeSearch(searchRequest)) {
            try {
                RestaurantType.valueOf(searchRequest.type().trim().toUpperCase());
                restaurants = repository.findAllByTypesContainingIgnoreCase(searchRequest.type().trim());
            } catch (IllegalArgumentException e) {
                restaurants = List.of();
            }
        } else if (searchRequest.minRating() != null) {
            restaurants = repository.findByRatingGreaterThanEqual(searchRequest.minRating());
        } else {
            restaurants = repository.findAll();
        }

        restaurants = applyAdditionalFilters(restaurants, searchRequest);
        return mapper.toSummaryResponseList(restaurants);
    }

    /**
     * Updates basic restaurant information.
     *
     * @param restaurantId the restaurant ID to update
     * @param request      the update request data
     * @return RestaurantResponse containing updated restaurant details
     */
    public RestaurantResponse updateRestaurant(String restaurantId, RestaurantUpdateRequest request) {
        validateRestaurantTypes(request.types());

        Restaurant restaurant = repository.getOrThrow(restaurantId);
        mapper.updateEntity(restaurant, request);
        return mapper.toResponse(repository.save(restaurant));
    }

    /**
     * Updates restaurant location.
     *
     * @param restaurantId the restaurant ID to update
     * @param request      the location update request
     * @return RestaurantResponse containing updated restaurant details
     */
    public RestaurantResponse updateRestaurantLocation(String restaurantId, RestaurantUpdateLocationRequest request) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);
        mapper.updateLocation(restaurant, request);
        return mapper.toResponse(repository.save(restaurant));
    }

    /**
     * Updates restaurant opening hours.
     *
     * @param restaurantId the restaurant ID to update
     * @param request      the opening hours update request
     * @return RestaurantResponse containing updated restaurant details
     */
    public RestaurantResponse updateRestaurantOpeningDays(String restaurantId, RestaurantUpdateOpeningDaysRequest request) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);
        mapper.updateOpeningDays(restaurant, request);
        return mapper.toResponse(repository.save(restaurant));
    }

    /**
     * Adds a new review to a restaurant.
     * Uses @Transactional because this operation involves multiple steps:
     * 1. Reading restaurant, 2. Adding review, 3. Recalculating rating, 4. Saving
     */
    @Transactional
    public RestaurantResponse addReview(String restaurantId, ReviewCreateRequest request) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);

        if (request.rating() != null && (request.rating() < 1.0 || request.rating() > 5.0)) {
            throw new IllegalArgumentException("Rating must be between 1.0 and 5.0, got: " + request.rating());
        }

        Restaurant.Review review = mapper.toReviewEntity(request);
        review.setCreatedAt(LocalDateTime.now());

        if (restaurant.getReviews() == null) {
            restaurant.setReviews(new java.util.ArrayList<>());
        }
        restaurant.getReviews().add(review);

        updateRestaurantRating(restaurant);
        return mapper.toResponse(repository.save(restaurant));
    }

    /**
     * Gets all reviews for a restaurant.
     *
     * @param restaurantId the restaurant ID to get reviews for
     * @return List of ReviewResponse containing all reviews for the restaurant
     */
    public List<ReviewResponse> getRestaurantReviews(String restaurantId) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);
        return mapper.toReviewResponseList(restaurant.getReviews());
    }

    /**
     * Gets location information for a restaurant.
     *
     * @param restaurantId the restaurant ID
     * @return LocationResponse containing restaurant's location details
     */
    public LocationResponse getRestaurantLocation(String restaurantId) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);
        return mapper.toLocationResponse(restaurant);
    }

    /**
     * Gets opening hours information for a restaurant.
     *
     * @param restaurantId the restaurant ID
     * @return OpeningDaysResponse containing restaurant's opening hours
     */
    public OpeningDaysResponse getRestaurantOpeningDays(String restaurantId) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);
        return mapper.toOpeningDaysResponse(restaurant);
    }

    /**
     * Deletes a restaurant by its ID.
     *
     * @param restaurantId the restaurant ID to delete
     */
    public void deleteRestaurant(String restaurantId) {
        Restaurant restaurant = repository.getOrThrow(restaurantId);
        repository.delete(restaurant);
    }


    /**
     * Validates restaurant types to ensure they are valid enum values.
     */
    private void validateRestaurantTypes(List<RestaurantType> types) {
        if (types == null || types.isEmpty()) {
            throw new IllegalArgumentException("Restaurant types cannot be null or empty");
        }

        for (RestaurantType type : types) {
            if (type == null) {
                throw new IllegalArgumentException("Restaurant type cannot be null");
            }
        }
    }

    /**
     * Checks if search request contains location search criteria.
     */
    private boolean hasLocationSearch(RestaurantSearchRequest searchRequest) {
        return searchRequest.longitude() != null &&
                searchRequest.latitude() != null &&
                searchRequest.radiusInMeters() != null;
    }

    /**
     * Checks if search request contains name search criteria.
     */
    private boolean hasNameSearch(RestaurantSearchRequest searchRequest) {
        return searchRequest.name() != null && !searchRequest.name().trim().isEmpty();
    }

    /**
     * Checks if search request contains type search criteria.
     */
    private boolean hasTypeSearch(RestaurantSearchRequest searchRequest) {
        return searchRequest.type() != null && !searchRequest.type().trim().isEmpty();
    }

    /**
     * Helper method to find restaurants near a location.
     */
    private List<Restaurant> findRestaurantsNearLocation(RestaurantSearchRequest searchRequest) {
        Point location = new Point(searchRequest.longitude(), searchRequest.latitude());
        Distance distance = new Distance(searchRequest.radiusInMeters(), Metrics.NEUTRAL);
        return repository.findByLocationNear(location, distance);
    }

    /**
     * Helper method to apply additional filters to restaurant list.
     */
    private List<Restaurant> applyAdditionalFilters(List<Restaurant> restaurants, RestaurantSearchRequest searchRequest) {
        return restaurants.stream()
                .filter(restaurant -> matchesNameFilter(restaurant, searchRequest))
                .filter(restaurant -> matchesTypeFilter(restaurant, searchRequest))
                .filter(restaurant -> matchesRatingFilter(restaurant, searchRequest))
                .toList();
    }

    /**
     * Checks if restaurant matches name filter.
     */
    private boolean matchesNameFilter(Restaurant restaurant, RestaurantSearchRequest searchRequest) {
        if (!hasNameSearch(searchRequest)) {
            return true;
        }
        return restaurant.getName() != null &&
                restaurant.getName().toLowerCase().contains(searchRequest.name().toLowerCase());
    }

    /**
     * Checks if restaurant matches type filter.
     */
    private boolean matchesTypeFilter(Restaurant restaurant, RestaurantSearchRequest searchRequest) {
        if (!hasTypeSearch(searchRequest)) {
            return true;
        }
        if (restaurant.getTypes() == null || restaurant.getTypes().isEmpty()) {
            return false;
        }
        return restaurant.getTypes().stream()
                .anyMatch(type -> type.name().toLowerCase().contains(searchRequest.type().toLowerCase()));
    }

    /**
     * Checks if restaurant matches rating filter.
     */
    private boolean matchesRatingFilter(Restaurant restaurant, RestaurantSearchRequest searchRequest) {
        if (searchRequest.minRating() == null) {
            return true;
        }
        return restaurant.getRating() != null && restaurant.getRating() >= searchRequest.minRating();
    }

    /**
     * Recalculates and updates restaurant rating based on reviews.
     */
    private void updateRestaurantRating(Restaurant restaurant) {
        if (restaurant.getReviews() == null || restaurant.getReviews().isEmpty()) {
            restaurant.setRating(null);
            restaurant.setRatingCount(0);
            return;
        }

        List<Restaurant.Review> reviews = restaurant.getReviews();

        List<Double> validRatings = reviews.stream()
                .map(Restaurant.Review::getRating)
                .filter(Objects::nonNull)
                .filter(rating -> rating >= 1.0 && rating <= 5.0)
                .toList();

        if (validRatings.isEmpty()) {
            restaurant.setRating(null);
            restaurant.setRatingCount(0);
            return;
        }

        double averageRating = validRatings.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);

        double roundedRating = Math.round(averageRating * 10.0) / 10.0;

        restaurant.setRating(roundedRating);
        restaurant.setRatingCount(validRatings.size());
    }
}