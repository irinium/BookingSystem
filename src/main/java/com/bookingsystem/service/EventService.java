package com.bookingsystem.service;

import com.bookingsystem.repository.EventRepository;
import com.bookingsystem.repository.entity.EventEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public void logEvent(String eventType, String description) {
        EventEntity event = new EventEntity();
        event.setEventType(eventType);
        event.setEventTimestamp(LocalDateTime.now());
        event.setDescription(description);
        eventRepository.save(event);
    }
}
