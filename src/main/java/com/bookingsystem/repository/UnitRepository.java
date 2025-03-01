package com.bookingsystem.repository;

import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.UnitEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {

    @Query("SELECT u FROM UnitEntity u " +
            "WHERE (:minCost IS NULL OR u.cost >= :minCost) " +
            "AND (:maxCost IS NULL OR u.cost <= :maxCost) " +
            "AND (:accommodationType IS NULL OR u.accommodationType = :accommodationType) " +
            "AND NOT EXISTS (SELECT b FROM BookingEntity b WHERE b.unit.id = u.id " +
            "AND b.bookingStartDate < :endDate " +
            "AND b.bookingEndDate > :startDate)")
    Page<UnitEntity> findAvailableUnits(
            @Param("minCost") BigDecimal minCost,
            @Param("maxCost") BigDecimal maxCost,
            @Param("accommodationType") AccommodationType accommodationType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    @Query("SELECT u FROM UnitEntity u WHERE u.available = true")
    Page<UnitEntity> getAvailableUnits(Pageable pageable);
}
