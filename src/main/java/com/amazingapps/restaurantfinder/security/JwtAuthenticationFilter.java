package com.amazingapps.restaurantfinder.security;

import com.amazingapps.restaurantfinder.exception.AppRuntimeException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserLoginRequest loginRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UserLoginRequest.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(), loginRequest.getPassword());
            return getAuthenticationManager().authenticate(authToken);
        } catch (IOException e) {
            throw new AppRuntimeException(e);
        }
    }
}