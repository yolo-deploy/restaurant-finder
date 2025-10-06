package com.amazingapps.restaurantfinder.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Represents a user entity stored in MongoDB.
 * Contains user email, password hash, and associated restaurant IDs.
 */
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
public class User extends AbstractDocument {

    /**
     * User's email address. Must be unique.
     */
    @Indexed(unique = true)
    @Field("email")
    private String email;

    /**
     * Hashed password of the user.
     */
    @Field("password_hash")
    private String passwordHash;

    /**
     * List of restaurant IDs associated with the user.
     */
    @Field("restaurants")
    private List<String> restaurantIds;
}