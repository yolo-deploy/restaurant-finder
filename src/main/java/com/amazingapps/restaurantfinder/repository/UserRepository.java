package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for User entities.
 * Provides methods for user-specific queries.
 */
@Repository
public interface UserRepository extends AbstractRepository<User, String>{

    /**
     * Finds a user by email, ignoring case.
     * @param email user's email address
     * @return Optional containing the user if found, or empty otherwise
     */
    Optional<User> findByEmailIgnoreCase(String email);
}