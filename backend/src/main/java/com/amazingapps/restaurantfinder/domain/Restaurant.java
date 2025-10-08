package com.amazingapps.restaurantfinder.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a restaurant entity stored in MongoDB.
 * Contains information about the restaurant such as name, rating, location, types, reviews, and opening hours.
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "restaurants")
public class Restaurant extends AbstractDocument {

    /**
     * The name of the restaurant.
     */
    @Field("name")
    @Indexed(unique = true)
    private String name;
    /**
     * The average rating of the restaurant.
     */
    @Field("rating")
    private Double rating;
    /**
     * The price level of the restaurant (e.g., 1-5).
     */
    @Field("price_level")
    private Integer priceLevel;
    /**
     * The phone number of the restaurant.
     */
    @Field("phone_number")
    private String phoneNumber;
    /**
     * The list of types/cuisines associated with the restaurant.
     */
    @Indexed
    @Field("types")
    private List<RestaurantType> types;
    /**
     * The number of ratings received by the restaurant.
     */
    @Field("rating_count")
    private Integer ratingCount;
    /**
     * The list of reviews for the restaurant.
     */
    @Field("reviews")
    private List<Review> reviews;
    /**
     * The formatted address of the restaurant.
     */
    @Field("formatted_address")
    private String formattedAddress;
    /**
     * The opening days information for the restaurant.
     */
    @Field("opening_hours")
    private OpeningDays openingDays;

    /**
     * The geographic location of the restaurant (longitude, latitude).
     */
    @Field("location")
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    /**
     * Represents the opening days of a restaurant.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class OpeningDays {
        /**
         * Indicates if the restaurant is currently open.
         */
        private Boolean openNow;
        /**
         * List of opening hours for each weekday.
         */
        private List<WeekDay> weekdays;
    }

    /**
     * Represents a review for a restaurant.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Review {
        /**
         * The name of the review's author.
         */
        private String authorName;
        /**
         * The rating given by the author.
         */
        private Double rating;
        /**
         * The review text.
         */
        private String text;
        /**
         * The relative time description of the review (e.g., '2 days ago').
         */
        private LocalDateTime relativeTimeDescription;
        /**
         * The date and time when the review was created.
         */
        private LocalDateTime createdAt;
    }
}