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

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final TokenInteract tokenInteract;
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserLoginResponse getToken(String id, UserLoginRequest request) {
        User user = null == id ? findByEmail(request.email()) : repository.getOrThrow(id);

        verifyPassword(request.password(), user.getPasswordHash());

        UserResponse response = mapper.toResponse(user);

        return mapper.toLoginResponse(response, tokenInteract.generateToken(loadUserByUsername(user.getEmail())));
    }

    public Boolean validateToken(HttpServletRequest request) {
        String token = tokenInteract.getToken(request);
        return tokenInteract.validateToken(token);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = repository.findByEmailIgnoreCase(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + name));

        return UserDetailsImpl.builder()
                .id(user.getId())
                .name(user.getEmail())
                .passwordHash(user.getPasswordHash()).build();
    }

    public User findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new BadCredentialsException(
                        "User not found with email: " + email
                ));
    }

    private void verifyPassword(String rawPassword, String passwordHash) {
        if (!PasswordUtil.matches(rawPassword, passwordHash)) {
            throw new BadCredentialsException("Invalid password");
        }
    }

    private void checkUniqueEmail(String email) {
        repository.findByEmailIgnoreCase(email)
                .ifPresent(u -> {
                    throw new ExecutionConflictException(
                            "User with email '" + email + "' already exists");
                });
    }
}