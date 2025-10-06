package com.amazingapps.restaurantfinder.mapper;

import org.springframework.stereotype.Component;

@Component
public interface AbstractMapper<E, D> {

    D toResponse(E entity);
}