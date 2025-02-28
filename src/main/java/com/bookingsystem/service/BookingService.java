package com.bookingsystem.service;

import com.bookingsystem.exception.NotValidArgumentException;
import com.bookingsystem.exception.UnitNotAvailableException;
import com.bookingsystem.exception.UnitNotFoundException;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.UnitEntity;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookingService {
    private UnitRepository unitRepository;
    private BookingRepository bookingRepository;

    public BookingResponse bookUnit(String unitId, @NotNull String userId, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        try {
            processBooking(unitId, userId, bookingStartDate, bookingEndDate);
        } catch (RuntimeException e) {
            return new BookingResponse().message(e.getMessage());
        }
        return new BookingResponse().message(String.format("Successfully booked unit with id %s for dates from %s till %s", unitId, bookingStartDate, bookingEndDate));
    }

    public void processBooking(String unitId, @NotNull String userId, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        long parsedUnitId = parseStringToLong(unitId);
        Optional<UnitEntity> unitOpt = unitRepository.findById(parsedUnitId);
        if (unitOpt.isEmpty()) {
            log.warn("Unit is not found by id {}", parsedUnitId);
            throw new UnitNotFoundException(String.format("Unit with id %s does not exist, booking cannot proceed", parsedUnitId));
        }

        int overlappingCount = bookingRepository.countOverlappingBookings(parsedUnitId, bookingStartDate, bookingEndDate);
        if (overlappingCount > 0) {
            log.warn("There is an overlapping booking, so the unit is not available");
            throw new UnitNotAvailableException(String.format("Unit with id %s is not available for iven dates from %s till %s", parsedUnitId, bookingStartDate, bookingEndDate));
        }

        BookingEntity booking = new BookingEntity();
        booking.setUnit(unitOpt.get());
        booking.setUserId(parseStringToLong(userId));
        booking.setBookingStartDate(bookingStartDate);
        booking.setBookingEndDate(bookingEndDate);

        bookingRepository.save(booking);
    }

    private static long parseStringToLong(String id) {
        long parsedUnitId;
        try {
            parsedUnitId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.error("Error parsing unit id {}", id);
            throw new NotValidArgumentException(String.format("Error parsing unit id %s", id));
        }
        return parsedUnitId;
    }

    public boolean cancelBooking(Long unitId, Long userId) {
        // TODO: Implement cancellation logic: update booking status, mark unit as available, etc.
        return true;
    }
}
