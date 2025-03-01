package com.bookingsystem.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.bookingsystem.constants.EntityConstants.*;

@Entity
@Table(name = TABLE_PAYMENT)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID)
    private Long id;

    @ManyToOne
    @JoinColumn(name = COLUMN_BOOKING_ID, nullable = false)
    private BookingEntity booking;

    @Column(name = COLUMN_AMOUNT, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = COLUMN_PAYMENT_DATE, nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = COLUMN_PAYMENT_STATUS, nullable = false)
    private PaymentStatus paymentStatus;
}
