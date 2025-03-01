package com.bookingsystem.repository;

import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.UnitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@ActiveProfiles("test")
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UnitRepository unitRepository;

    @Test
    public void testCountOverlappingBookings() {
        UnitEntity unit = new UnitEntity();
        unit.setAvailable(true);
        unit.setNumberOfRooms(3);
        unit.setAccommodationType(AccommodationType.HOME);
        unit.setFloor(2);
        unit.setCost(BigDecimal.valueOf(120.00));
        unit.setDescription("Test Unit");
        UnitEntity savedUnit = unitRepository.save(unit);

        BookingEntity booking = new BookingEntity();
        booking.setUnit(savedUnit);
        booking.setUserId(1001L);
        booking.setBookingStartDate(LocalDate.of(2025, 3, 1));
        booking.setBookingEndDate(LocalDate.of(2025, 3, 15));
        bookingRepository.save(booking);

        int overlappingCount = bookingRepository.countOverlappingBookings(
                savedUnit.getId(),
                LocalDate.of(2025, 3, 10),
                LocalDate.of(2025, 3, 20));
        assertEquals(1, overlappingCount);

        int noOverlapCount = bookingRepository.countOverlappingBookings(
                savedUnit.getId(),
                LocalDate.of(2025, 3, 16),
                LocalDate.of(2025, 3, 20));
        assertEquals(0, noOverlapCount);
    }

    @Test
    public void testFindByUnitIdAndUserId() {
        UnitEntity unit = new UnitEntity();
        unit.setAvailable(true);
        unit.setNumberOfRooms(2);
        unit.setAccommodationType(AccommodationType.HOME);
        unit.setFloor(1);
        unit.setCost(BigDecimal.valueOf(200.00));
        unit.setDescription("Home Unit");
        UnitEntity savedUnit = unitRepository.save(unit);

        BookingEntity booking = new BookingEntity();
        booking.setUnit(savedUnit);
        booking.setUserId(1002L);
        booking.setBookingStartDate(LocalDate.of(2025, 4, 1));
        booking.setBookingEndDate(LocalDate.of(2025, 4, 15));
        BookingEntity savedBooking = bookingRepository.save(booking);

        Optional<BookingEntity> foundBooking = bookingRepository.findByUnitIdAndUserId(savedUnit.getId(), 1002L);
        assertTrue(foundBooking.isPresent());
        assertEquals(savedBooking.getId(), foundBooking.get().getId());

        Optional<BookingEntity> notFound = bookingRepository.findByUnitIdAndUserId(savedUnit.getId(), 999L);
        assertFalse(notFound.isPresent());
    }
}
