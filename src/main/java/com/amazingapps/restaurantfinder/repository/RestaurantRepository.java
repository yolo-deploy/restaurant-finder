package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.domain.Restaurant;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository-Interface für Restaurant-Entitäten.
 * Bietet typische Abfragen basierend auf den Feldern in {@link Restaurant}.
 */
@Repository
public interface RestaurantRepository extends AbstractRepository<Restaurant, String> {

    /**
     * Sucht Restaurants, deren Name den Text enthält (groß-/kleinschreibungsunabhängig).
     */
    List<Restaurant> findByNameContainingIgnoreCase(String name);

    /**
     * Sucht Restaurants, die den angegebenen Typ in der Liste der Typen enthalten (z. B. "Italian").
     * Da `types` eine Liste von Strings ist, matcht dies ein Element der Liste.
     */
    List<Restaurant> findByTypes(String type);

    /**
     * Sucht Restaurants mit einer Bewertung größer oder gleich dem angegebenen Wert.
     */
    List<Restaurant> findByRatingGreaterThanEqual(Double minRating);

    /**
     * Sucht Restaurants in der Nähe eines Punktes innerhalb einer Distanz (Geosuche).
     */
    List<Restaurant> findByLocationNear(Point location, Distance distance);
}
