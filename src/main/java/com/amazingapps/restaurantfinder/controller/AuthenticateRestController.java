package com.amazingapps.restaurantfinder.controller;

import com.amazingapps.restaurantfinder.dto.user.UserLoginRequest;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authenticate")
public class AuthenticateRestController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<UserLoginResponse> authenticate(@Valid @RequestBody UserLoginRequest request) {
        UserLoginResponse response = service.getToken(null, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<Boolean> validate(HttpServletRequest request) {
        Boolean response = service.validateToken(request);
        return ResponseEntity.ok(response);
    }
}