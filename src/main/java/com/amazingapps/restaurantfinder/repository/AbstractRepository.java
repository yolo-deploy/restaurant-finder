package com.amazingapps.restaurantfinder.repository;

import com.amazingapps.restaurantfinder.exception.NotFoundObjectException;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base interface for all MongoDB repositories in the application.
 * Provides common methods for entity retrieval and naming.
 * @param <T> entity type
 * @param <I> entity ID type
 */
@NoRepositoryBean
public interface AbstractRepository<T, I> extends MongoRepository<T, I> {

    /**
     * Finds an entity by its ID or throws NotFoundObjectException if not found.
     * @param id entity ID
     * @return entity instance
     */
    default T getOrThrow(I id) {
        return findById(id)
                .orElseThrow(() ->
                        new NotFoundObjectException(getEntityName() + " not found with id: " + id));
    }

    /**
     * Returns the simple name of the entity (without 'Repository' suffix).
     * @return entity name
     */
    default String getEntityName() {
        return getClass().getSimpleName().replace("Repository", "");
    }
}