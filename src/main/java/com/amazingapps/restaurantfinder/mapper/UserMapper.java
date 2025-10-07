package com.amazingapps.restaurantfinder.mapper;

import com.amazingapps.restaurantfinder.domain.User;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting User entities to UserResponse and UserLoginResponse DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserResponse> {

    /**
     * Converts a User entity to UserResponse DTO.
     * @param entity User entity
     * @return UserResponse DTO
     */
    UserResponse toResponse(User entity);

    /**
     * Creates a UserLoginResponse DTO from UserResponse and token.
     * @param user UserResponse DTO
     * @param token JWT token string
     * @return UserLoginResponse DTO
     */
    @Mapping(target = "token", source = "token")
    @Mapping(target = "user", source = "user")
    UserLoginResponse toLoginResponse(UserResponse user, String token);

    /**
     * Converts email and password hash to a User entity.
     * @param email user's email
     * @param passwordHash hashed password
     * @return User entity
     */
    @Mapping(target = "email", source = "email")
    @Mapping(target = "passwordHash", source = "passwordHash")
    User toEntity(String email, String passwordHash);
}