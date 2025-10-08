package com.amazingapps.restaurantfinder.security;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of UserDetails for Spring Security authentication.
 * Stores user ID, name, and password hash.
 */
@Builder
public class UserDetailsImpl implements UserDetails {

    private String id;
    private String passwordHash;

    /**
     * Returns authorities granted to the user (empty for this implementation).
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    /**
     * Returns the user's password (hash).
     */
    @Override
    public String getPassword() {
        return passwordHash;
    }

    /**
     * Returns the user's username (id).
     */
    @Override
    public String getUsername() {
        return id;
    }
}