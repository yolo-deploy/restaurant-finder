package com.amazingapps.restaurantfinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a restaurant entity stored in MongoDB.
 * Contains information about the restaurant such as name, rating, location, types, reviews, and opening hours.
 */
@Document(collection = "restaurants")
public class Restaurant extends AbstractDocument {

    /**
     * The name of the restaurant.
     */
    @Indexed
    private String name;
    /**
     * The average rating of the restaurant.
     */
    private Double rating;
    /**
     * The price level of the restaurant (e.g., 1-5).
     */
    private Integer priceLevel;
    /**
     * The phone number of the restaurant.
     */
    private String phoneNumber;

    /**
     * The types/categories of the restaurant (e.g., Italian, Sushi).
     */
    @Indexed
    private List<String> types;
    /**
     * The number of ratings received by the restaurant.
     */
    private Integer ratingCount;
    /**
     * The list of reviews for the restaurant.
     */
    private List<Review> reviews;
    /**
     * The formatted address of the restaurant.
     */
    private String formattedAddress;
    /**
     * The opening hours information for the restaurant.
     */
    private OpeningHours openingHours;

    /**
     * The geographic location of the restaurant (longitude, latitude).
     */
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    /**
     * Represents the opening hours of a restaurant.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpeningHours {
        /**
         * Indicates if the restaurant is currently open.
         */
        private Boolean openNow;
        /**
         * List of opening hours for each weekday.
         */
        private List<String> weekdayText;
    }

    /**
     * Represents a review for a restaurant.
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
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