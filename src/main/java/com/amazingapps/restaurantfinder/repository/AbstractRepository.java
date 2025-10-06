package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.exception.NotFoundObjectException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository<T, ID> extends MongoRepository<T, ID> {

    default T getOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() ->
                        new NotFoundObjectException(getEntityName() + " not found with id: " + id));
    }

    default String getEntityName() {
        return getClass().getSimpleName().replace("Repository", "");
    }
}