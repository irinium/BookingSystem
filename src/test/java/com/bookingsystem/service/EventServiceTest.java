package com.bookingsystem.service;

import com.bookingsystem.repository.EventRepository;
import com.bookingsystem.repository.entity.EventEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class EventServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testLogEvent() {
        EventService eventService = new EventService(eventRepository);

        String eventType = "TEST_EVENT";
        String description = "This is a test event";

        eventService.logEvent(eventType, description);

        List<EventEntity> events = eventRepository.findAll();
        assertEquals(1, events.size(), "There should be exactly one event in the repository");

        EventEntity event = events.get(0);
        assertEquals(eventType, event.getEventType(), "Event type should match");
        assertEquals(description, event.getDescription(), "Event description should match");
        assertNotNull(event.getEventTimestamp(), "Event timestamp should not be null");
    }
}
