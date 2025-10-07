package com.amazingapps.restaurantfinder.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * Repräsentiert eine Nachricht, die ein User an ein Restaurant schickt.
 * Wird als eigenständige Collection in MongoDB gespeichert.
 */
@Document(collection = "messages")
public record Message(
        @Id String id,            // MongoDB ID der Nachricht
        String userId,            // Wer hat die Nachricht geschrieben
        String restaurantId,      // An welches Restaurant geht die Nachricht
        String content,           // Text der Nachricht
        LocalDateTime sentAt      // Zeitpunkt des Versendens
) {}
