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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "restaurants")
public class Restaurant extends AbstractDocument {

    @Indexed
    private String name;
    private Double rating;
    private Integer priceLevel;
    private String phoneNumber;

    @Indexed
    private List<String> types;
    private Integer ratingCount;
    private List<Review> reviews;
    private String formattedAddress;
    private OpeningHours openingHours;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpeningHours {
        private Boolean openNow;
        private List<String> weekdayText;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Review {
        private String authorName;
        private Double rating;
        private String text;
        private LocalDateTime relativeTimeDescription;
        private LocalDateTime createdAt;
    }
}