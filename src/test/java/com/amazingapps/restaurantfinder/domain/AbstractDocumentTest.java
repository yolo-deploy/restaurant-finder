package com.amazingapps.restaurantfinder.domain;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class AbstractDocumentTest {
    static class TestDocument extends AbstractDocument {}

    @Test
    void gettersAndSetters_shouldWork() {
        TestDocument doc = new TestDocument();
        doc.setId("123");
        LocalDateTime now = LocalDateTime.now();
        doc.setCreationDate(now);
        doc.setModifyDate(now.plusDays(1));
        assertEquals("123", doc.getId());
        assertEquals(now, doc.getCreationDate());
        assertEquals(now.plusDays(1), doc.getModifyDate());
    }
}

