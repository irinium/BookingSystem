package com.bookingsystem.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import static com.bookingsystem.constants.EntityConstants.*;

@Entity
@Table(name = TABLE_UNIT)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_generator")
    @SequenceGenerator(name = "unit_generator", sequenceName = "unit_seq", allocationSize = 1)
    @Column(name = COLUMN_ID)
    private Long id;

    @Column(name = COLUMN_NUMBER_OF_ROOMS)
    private Integer numberOfRooms;

    @Enumerated(EnumType.STRING)
    @Column(name = COLUMN_ACCOMMODATION_TYPE)
    private AccommodationType accommodationType;

    @Column(name = COLUMN_FLOOR)
    private Integer floor;

    @Column(name = COLUMN_COST)
    private BigDecimal cost;

    @Column(name = COLUMN_DESCRIPTION)
    private String description;

    @Column(name = COLUMN_AVAILABLE)
    private Boolean available;
}
