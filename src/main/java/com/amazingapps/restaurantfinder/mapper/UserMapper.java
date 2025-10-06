package com.amazingapps.restaurantfinder.mapper;

import com.amazingapps.restaurantfinder.domain.User;
import com.amazingapps.restaurantfinder.dto.user.UserLoginResponse;
import com.amazingapps.restaurantfinder.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends AbstractMapper<User, UserResponse> {

    UserResponse toResponse(User entity);

    @Mapping(target = "token", source = "token")
    @Mapping(target = "user", source = "user")
    UserLoginResponse toLoginResponse(UserResponse user, String token);
}