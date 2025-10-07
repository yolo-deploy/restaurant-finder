package com.amazingapps.restaurantfinder.repository;
import com.amazingapps.restaurantfinder.domain.Review;

import com.amazingapps.restaurantfinder.domain.Review;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert ein Restaurant-Dokument in der MongoDB-Datenbank.
 * Wird in der Collection "restaurants" gespeichert.
 */
@Document(collection = "restaurants")
public class Restaurant {

    @Id
    private String id;

    private String name;
    private String address;
    private String description;
    private String imageUrl;

    private double averageRating;

    // Liste der Bewertungen, falls ihr ein Review-Objekt habt
    private List<Review> reviews = new ArrayList<>();

    //  Standard-Konstruktor (wichtig für Spring Data)
    public Restaurant() {}

    //  Hilfskonstruktor (zum schnellen Erstellen von Restaurants)
    public Restaurant(String name, String address, String description, String imageUrl) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    //  Getter und Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    //  Methode zum Hinzufügen einer Bewertung
    public void addReview(Review review) {
        if (reviews == null) {
            reviews = new ArrayList<>();
        }
        reviews.add(review);
        recalculateAverageRating();
    }

    //  Durchschnitt neu berechnen, wenn neue Bewertungen hinzukommen
    private void recalculateAverageRating() {
        if (reviews == null || reviews.isEmpty()) {
            this.averageRating = 0.0;
            return;
        }
        double sum = 0.0;
        for (Review r : reviews) {
            Double rt = r.getRating();
            sum += (rt != null ? rt : 0.0);
        }
        this.averageRating = sum / reviews.size();
    }

}
