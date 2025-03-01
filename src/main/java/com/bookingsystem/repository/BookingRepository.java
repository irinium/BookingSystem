package com.bookingsystem.repository;

import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    @Query("SELECT COUNT(b) FROM BookingEntity b " +
            "WHERE b.unit.id = :unitId " +
            "AND b.bookingStartDate < :endDate " +
            "AND b.bookingEndDate > :startDate")
    int countOverlappingBookings(@Param("unitId") Long unitId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);

    Optional<BookingEntity> findByUnitIdAndUserId(Long unitId, Long userId);
}
