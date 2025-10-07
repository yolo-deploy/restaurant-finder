package com.amazingapps.restaurantfinder.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Represents a user message sent to a restaurant.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "messages")
public class Message {

    /**
     * Unique identifier for the message (MongoDB ObjectId).
     */
    @Id
    private String id;

    /**
     * The ID of the user who sent the message.
     */
    private String userId;

    /**
     * The message content text.
     */
    private String content;

    /**
     * The date and time when the message was sent.
     */
    private LocalDateTime sentAt = LocalDateTime.now();

    /**
     * Optional: whether the message has been read by the restaurant admin.
     */
    private boolean read = false;
}
