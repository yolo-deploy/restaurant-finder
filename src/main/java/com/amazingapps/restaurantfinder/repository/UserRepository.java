package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends AbstractRepository<User, String>{

    Optional<User> findByEmailIgnoreCase(String email);
}