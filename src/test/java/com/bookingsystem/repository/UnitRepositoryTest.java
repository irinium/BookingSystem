package com.bookingsystem.repository;

import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.UnitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class UnitRepositoryTest {

    @Autowired
    private UnitRepository unitRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Test
    public void testGetAvailableUnits() {
        UnitEntity unit1 = new UnitEntity();
        unit1.setNumberOfRooms(2);
        unit1.setAccommodationType(AccommodationType.FLAT);
        unit1.setFloor(1);
        unit1.setCost(BigDecimal.valueOf(150));
        unit1.setDescription("Unit 1");
        unit1.setAvailable(true);
        unitRepository.save(unit1);

        UnitEntity unit2 = new UnitEntity();
        unit2.setNumberOfRooms(3);
        unit2.setAccommodationType(AccommodationType.HOME);
        unit2.setFloor(2);
        unit2.setCost(BigDecimal.valueOf(200));
        unit2.setDescription("Unit 2");
        unit2.setAvailable(false);
        unitRepository.save(unit2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<UnitEntity> availableUnits = unitRepository.getAvailableUnits(pageable);
        assertEquals(1, availableUnits.getTotalElements());
    }

    @Test
    public void testFindAvailableUnitsNoBookingOverlap() {
        UnitEntity unit = new UnitEntity();
        unit.setNumberOfRooms(2);
        unit.setAccommodationType(AccommodationType.APARTMENTS);
        unit.setFloor(3);
        unit.setCost(BigDecimal.valueOf(180));
        unit.setDescription("Test Unit");
        unit.setAvailable(true);
        UnitEntity savedUnit = unitRepository.save(unit);

        Pageable pageable = PageRequest.of(0, 10);
        Page<UnitEntity> result = unitRepository.findAvailableUnits(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200),
                AccommodationType.APARTMENTS,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                pageable);
        assertEquals(1, result.getTotalElements());
        assertEquals(savedUnit.getId(), result.getContent().get(0).getId());
    }

    @Test
    public void testFindAvailableUnitsWithBookingOverlap() {
        UnitEntity unit = new UnitEntity();
        unit.setNumberOfRooms(2);
        unit.setAccommodationType(AccommodationType.HOME);
        unit.setFloor(1);
        unit.setCost(BigDecimal.valueOf(120));
        unit.setDescription("Overlap Unit");
        unit.setAvailable(true);
        UnitEntity savedUnit = unitRepository.save(unit);

        BookingEntity booking = new BookingEntity();
        booking.setUnit(savedUnit);
        booking.setUserId(1001L);
        booking.setBookingStartDate(LocalDate.of(2025, 3, 5));
        booking.setBookingEndDate(LocalDate.of(2025, 3, 15));
        bookingRepository.save(booking);

        Pageable pageable = PageRequest.of(0, 10);
        Page<UnitEntity> result = unitRepository.findAvailableUnits(
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(200),
                AccommodationType.HOME,
                LocalDate.of(2025, 3, 1),
                LocalDate.of(2025, 3, 10),
                pageable);
        assertEquals(1, result.getTotalElements());
    }
}