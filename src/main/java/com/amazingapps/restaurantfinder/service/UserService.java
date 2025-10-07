package com.amazingapps.restaurantfinder.service;

import com.amazingapps.restaurantfinder.domain.User;
import com.amazingapps.restaurantfinder.dto.user.UserLoginRequest;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import com.amazingapps.restaurantfinder.exception.ExecutionConflictException;
import com.amazingapps.restaurantfinder.mapper.UserMapper;
import com.amazingapps.restaurantfinder.repository.UserRepository;
import com.amazingapps.restaurantfinder.security.TokenInteract;
import com.amazingapps.restaurantfinder.security.UserDetailsImpl;
import com.amazingapps.restaurantfinder.util.PasswordUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service class for user-related operations such as authentication, token management, and user lookup.
 * Implements Spring Security's UserDetailsService for authentication integration.
 */
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final TokenInteract tokenInteract;
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Authenticates a user by ID or email and returns a login response with JWT token.
     * @param id user ID (nullable)
     * @param request user login request
     * @return login response containing JWT token and user info
     */
    public UserLoginResponse getToken(String id, UserLoginRequest request) {
        User user = null == id ? findByEmail(request.email()) : repository.getOrThrow(id);

        verifyPassword(request.password(), user.getPasswordHash());

        UserResponse response = mapper.toResponse(user);

        return mapper.toLoginResponse(response, tokenInteract.generateToken(loadUserByUsername(user.getEmail())));
    }

    /**
     * Validates the JWT token from the HTTP request.
     * @param request HTTP servlet request
     * @return true if token is valid, false otherwise
     */
    public Boolean validateToken(HttpServletRequest request) {
        String token = tokenInteract.getToken(request);
        return tokenInteract.validateToken(token);
    }

    /**
     * Loads user details by username (email) for authentication.
     * @param name user's email address
     * @return UserDetails implementation
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = repository.findByEmailIgnoreCase(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));

        return UserDetailsImpl.builder()
                .id(user.getId())
                .name(user.getEmail())
                .passwordHash(user.getPasswordHash()).build();
    }

    /**
     * Finds a user by email or throws BadCredentialsException if not found.
     * @param email user's email address
     * @return User entity
     */
    public User findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException(
                        "User not found with email: " + email
                ));
    }

    /**
     * Verifies the raw password against the stored password hash.
     * Throws BadCredentialsException if the password is invalid.
     * @param rawPassword plain text password
     * @param passwordHash hashed password
     */
    private void verifyPassword(String rawPassword, String passwordHash) {
        if (!PasswordUtil.matches(rawPassword, passwordHash)) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    /**
     * Checks if the email is unique in the database.
     * Throws ExecutionConflictException if a user with the email already exists.
     * @param email user's email address
     */
    private void checkUniqueEmail(String email) {
        repository.findByEmailIgnoreCase(email)
                .ifPresent(u -> {
                    throw new ExecutionConflictException(
                            "User with email '" + email + "' already exists");
                });
    }
}