package com.amazingapps.restaurantfinder.repository;



import com.amazingapps.restaurantfinder.domain.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByRestaurantId(String restaurantId);
    List<Message> findByUserId(String userId);
}
