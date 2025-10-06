package com.amazingapps.restaurantfinder.mapper;

import com.amazingapps.restaurantfinder.domain.User;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void toResponse_shouldMapUserToUserResponse() {
        User user = new User();
        user.setEmail("test@example.com");
        UserResponse response = userMapper.toResponse(user);
        assertNotNull(response);
        assertEquals("test@example.com", response.email());
    }

    @Test
    void toLoginResponse_shouldMapUserResponseAndToken() {
        UserResponse userResponse = new UserResponse("login@example.com");
        String token = "jwt-token";
        UserLoginResponse loginResponse = userMapper.toLoginResponse(userResponse, token);
        assertNotNull(loginResponse);
        assertEquals(token, loginResponse.token());
        assertEquals(userResponse, loginResponse.user());
    }
}
