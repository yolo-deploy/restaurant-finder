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
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.geo.Point;

import java.util.List;

/**
 * Elegant mapper for converting between Restaurant entities and various DTOs.
 * Provides clean and comprehensive mapping methods for all restaurant-related operations.
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper extends AbstractMapper<Restaurant, RestaurantResponse> {

    /**
     * Converts RestaurantCreateRequest to Restaurant entity.
     * Creates a complete new restaurant with all necessary fields including location and opening hours.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "name", source = "Name")
    @Mapping(target = "location", expression = "java(createPoint(request.latitude(), request.longitude()))")
    @Mapping(target = "openingDays", expression = "java(createOpeningDays(request.openNow(), request.weekdays()))")
    Restaurant toEntity(RestaurantCreateRequest request);

    /**
     * Creates a partial Restaurant entity from RestaurantUpdateRequest.
     * Useful for creating restaurant templates or partial updates.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "openingDays", ignore = true)
    @Mapping(target = "name", source = "Name")
    Restaurant toEntity(RestaurantUpdateRequest request);

    /**
     * Creates a minimal Restaurant entity with only location data.
     * Useful for location-based queries and operations.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "priceLevel", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "openingDays", ignore = true)
    @Mapping(target = "location", expression = "java(createPoint(request.latitude(), request.longitude()))")
    Restaurant toEntity(RestaurantUpdateLocationRequest request);

    /**
     * Creates a minimal Restaurant entity with only opening hours data.
     * Useful for schedule-based operations and queries.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "priceLevel", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "openingDays", expression = "java(createOpeningDays(request.openNow(), request.weekdays()))")
    Restaurant toEntity(RestaurantUpdateOpeningDaysRequest request);

    /**
     * Updates existing Restaurant entity with RestaurantUpdateRequest data.
     * Preserves location and opening hours while updating basic information.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "openingDays", ignore = true)
    @Mapping(target = "name", source = "Name")
    void updateEntity(@MappingTarget Restaurant restaurant, RestaurantUpdateRequest request);

    /**
     * Updates restaurant location with precise geographic coordinates.
     * Preserves all other restaurant data while updating only location.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "priceLevel", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "openingDays", ignore = true)
    @Mapping(target = "location", expression = "java(createPoint(request.latitude(), request.longitude()))")
    void updateLocation(@MappingTarget Restaurant restaurant, RestaurantUpdateLocationRequest request);

    /**
     * Updates restaurant opening hours with new schedule information.
     * Preserves all other restaurant data while updating only opening hours.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "ratingCount", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "priceLevel", ignore = true)
    @Mapping(target = "phoneNumber", ignore = true)
    @Mapping(target = "types", ignore = true)
    @Mapping(target = "location", ignore = true)
    @Mapping(target = "openingDays", expression = "java(createOpeningDays(request.openNow(), request.weekdays()))")
    void updateOpeningDays(@MappingTarget Restaurant restaurant, RestaurantUpdateOpeningDaysRequest request);

    /**
     * Converts Restaurant entity to RestaurantSummaryResponse.
     * Provides essential information for lists and search results.
     */
    @Mapping(target = "types", expression = "java(convertTypesToStrings(restaurant.getTypes()))")
    @Mapping(target = "location", expression = "java(convertPointToLocationResponse(restaurant.getLocation()))")
    @Mapping(target = "openNow", expression = "java(restaurant.getOpeningDays() != null ? restaurant.getOpeningDays().getOpenNow() : null)")
    RestaurantSummaryResponse toSummaryResponse(Restaurant restaurant);

    /**
     * Converts list of Restaurant entities to list of RestaurantSummaryResponse.
     */
    List<RestaurantSummaryResponse> toSummaryResponseList(List<Restaurant> restaurants);

    /**
     * Helper method to convert RestaurantType list to String list.
     */
    default List<String> convertTypesToStrings(List<RestaurantType> types) {
        if (types == null) {
            return null;
        }
        return types.stream()
                .map(Enum::name)
                .toList();
    }

    /**
     * Helper method to convert Point to LocationResponse.
     */
    default LocationResponse convertPointToLocationResponse(Point point) {
        if (point == null) {
            return null;
        }
        return new LocationResponse(point.getY(), point.getX());
    }

    /**
     * Converts ReviewCreateRequest to Restaurant.Review entity.
     */
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    Restaurant.Review toReviewEntity(ReviewCreateRequest request);

    /**
     * Converts Restaurant.Review to ReviewResponse.
     */
    ReviewResponse toReviewResponse(Restaurant.Review review);

    /**
     * Converts list of Restaurant.Review to list of ReviewResponse.
     */
    List<ReviewResponse> toReviewResponseList(List<Restaurant.Review> reviews);

    /**
     * Converts Restaurant location to LocationResponse.
     */
    @Mapping(target = "latitude", expression = "java(restaurant.getLocation() != null ? restaurant.getLocation().getY() : null)")
    @Mapping(target = "longitude", expression = "java(restaurant.getLocation() != null ? restaurant.getLocation().getX() : null)")
    LocationResponse toLocationResponse(Restaurant restaurant);

    /**
     * Converts Restaurant opening days to OpeningDaysResponse.
     */
    @Mapping(target = "openNow", source = "openingDays.openNow")
    @Mapping(target = "weekDays", source = "openingDays.weekdays")
    OpeningDaysResponse toOpeningDaysResponse(Restaurant restaurant);

    /**
     * Creates a Point from latitude and longitude coordinates.
     */
    default Point createPoint(Double latitude, Double longitude) {
        if (latitude == null || longitude == null) {
            return null;
        }
        return new Point(longitude, latitude);
    }

    /**
     * Creates OpeningDays from openNow flag and weekdays list.
     */
    default Restaurant.OpeningDays createOpeningDays(Boolean openNow, List<WeekDay> weekdays) {
        if (openNow == null && (weekdays == null || weekdays.isEmpty())) {
            return null;
        }

        Restaurant.OpeningDays openingDays = new Restaurant.OpeningDays();
        openingDays.setOpenNow(openNow != null ? openNow : false);
        openingDays.setWeekdays(weekdays != null ? weekdays : List.of());
        return openingDays;
    }
}