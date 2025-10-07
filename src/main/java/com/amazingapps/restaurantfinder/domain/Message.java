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




    private String userId;

    /**
     * The message content text.
     */
    private String content;

    /**
     * The date and time when the message was sent.
     */
    private LocalDateTime sentAt = LocalDateTime.now();


    private boolean read = false;
}
