package com.bookingsystem.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.bookingsystem.constants.EntityConstants.*;

@Entity
@Table(name = TABLE_BOOKING)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_generator")
    @SequenceGenerator(name = "booking_generator", sequenceName = "booking_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = COLUMN_REF_UNIT_ID, nullable = false)
    private UnitEntity unit;

    @Column(name = COLUMN_START_DATE, nullable = false)
    private LocalDate bookingStartDate;

    @Column(name = COLUMN_END_DATE, nullable = false)
    private LocalDate bookingEndDate;

    @Column(name = COLUMN_USER_ID, nullable = false)
    private Long userId;
}
