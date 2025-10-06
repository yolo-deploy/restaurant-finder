package com.amazingapps.restaurantfinder.security;

import com.amazingapps.restaurantfinder.exception.ExecutionConflictException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Service for working with JWT tokens: generation, validation, and extracting user info.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenInteract {

    private static final String TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration.time}")
    private long expirationTime;

    /**
     * Extracts the username from the JWT token.
     * @param token JWT token string
     * @return username string
     */
    public String getUser(String token) {
        JwtParser jwtParser = getJwtParser();
        return jwtParser.parseSignedClaims(token).getPayload().getSubject();
    }

    /**
     * Validates the JWT token.
     * @param token JWT token string
     * @return true if valid, false otherwise
     */
    public boolean validateToken(String token) {
        String result;
        try {
            JwtParser jwtParser = getJwtParser();
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException _) {
            result = "Invalid JWT token";
        } catch (ExpiredJwtException _) {
            result = "Expired JWT token";
        } catch (UnsupportedJwtException _) {
            result = "Unsupported JWT token";
        } catch (IllegalArgumentException _) {
            result = "JWT claims string is empty";
        }
        throw new ExecutionConflictException(result);
    }

    public String getToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "Invalid authorization type");
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sessionId", UUID.randomUUID().toString());

        return Jwts.builder()
                .claims().empty().add(claims).and()
                .subject(userDetails.getUsername())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    private JwtParser getJwtParser() {
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes())).build();
    }
}