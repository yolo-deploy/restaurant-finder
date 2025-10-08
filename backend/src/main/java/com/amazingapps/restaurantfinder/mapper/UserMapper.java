package com.amazingapps.restaurantfinder.mapper;

import com.amazingapps.restaurantfinder.domain.User;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for converting User entities to UserResponse and UserLoginResponse DTOs.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserResponse> {

    /**
     * Creates a UserLoginResponse DTO from UserResponse and token.
     *
     * @param user  UserResponse DTO
     * @param token JWT token string
     * @return UserLoginResponse DTO
     */
    @Mapping(target = "token", source = "token")
    @Mapping(target = "user", source = "user")
    UserLoginResponse toLoginResponse(UserResponse user, String token);

    /**
     * Converts email and password hash to a User entity.
     *
     * @param email        user's email
     * @param passwordHash hashed password
     * @return User entity
     */
    @Mapping(target = "email", source = "email")
    @Mapping(target = "passwordHash", source = "passwordHash")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurantIds", ignore = true)
    @Mapping(target = "modifyDate", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    User toEntity(String email, String passwordHash);
}