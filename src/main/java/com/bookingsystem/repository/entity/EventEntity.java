package com.bookingsystem.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.bookingsystem.constants.EntityConstants.*;

@Entity
@Table(name = TABLE_EVENT)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = COLUMN_EVENT_TYPE, nullable = false, length = 50)
    private String eventType;

    @Column(name = COLUMN_EVENT_TIMESTAMP, nullable = false)
    private LocalDateTime eventTimestamp;

    @Column(name = COLUMN_DESCRIPTION, length = 255)
    private String description;
}
