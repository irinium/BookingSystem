package com.bookingsystem.service;

import com.bookingsystem.exception.BookingNotFoundException;
import com.bookingsystem.exception.UnitNotAvailableException;
import com.bookingsystem.exception.UnitNotFoundException;
import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.UnitEntity;
import com.bookingsystem.utils.ConversionUtils;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

import static com.bookingsystem.utils.ConversionUtils.createResponse;
import static com.bookingsystem.utils.ConversionUtils.parseStringToLong;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookingService {
    private UnitRepository unitRepository;
    private BookingRepository bookingRepository;

    public MessageResponse bookUnit(String unitId, String userId, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        try {
            processBooking(unitId, userId, bookingStartDate, bookingEndDate);
            markUnitAsRented(unitId);
            String message = String.format("Successfully booked unit with id %s for dates from %s till %s", unitId, bookingStartDate, bookingEndDate);
            return ConversionUtils.createResponse(message, 200);
        } catch (RuntimeException e) {
            return new MessageResponse().message(e.getMessage()).status(400);
        }
    }

    public void processBooking(String unitId, @NotNull String userId, LocalDate bookingStartDate, LocalDate bookingEndDate) {
        long parsedUnitId = parseStringToLong(unitId);
        Optional<UnitEntity> unitEntity = unitRepository.findById(parsedUnitId);
        if (unitEntity.isEmpty()) {
            log.warn("Unit is not found by id {}", parsedUnitId);
            throw new UnitNotFoundException(String.format("Unit with id %s does not exist, booking cannot proceed", parsedUnitId));
        }

        int overlappingCount = bookingRepository.countOverlappingBookings(parsedUnitId, bookingStartDate, bookingEndDate);
        if (overlappingCount > 0) {
            log.warn("There is an overlapping booking, so the unit is not available");
            throw new UnitNotAvailableException(String.format("Unit with id %s is not available for iven dates from %s till %s", parsedUnitId, bookingStartDate, bookingEndDate));
        }

        BookingEntity booking = new BookingEntity();
        booking.setUnit(unitEntity.get());
        booking.setUserId(parseStringToLong(userId));
        booking.setBookingStartDate(bookingStartDate);
        booking.setBookingEndDate(bookingEndDate);

        bookingRepository.save(booking);
    }

    public MessageResponse cancelBooking(String unitId, String userId) {
        try {
            boolean cancelled = processCancelingBooking(unitId, userId);
            if (cancelled) {
                markUnitAsAvailable(unitId);
                String message = String.format("Successfully cancelled booking for unit %s", unitId);
                return createResponse(message, 200);
            } else {
                return createResponse("Cancellation failed", 400);
            }
        } catch (BookingNotFoundException e) {
            return createResponse(e.getMessage(), 404);
        } catch (Exception e) {
            return createResponse(e.getMessage(), 500);
        }
    }

    public boolean processCancelingBooking(String unitId, String userId) {

        Optional<BookingEntity> booking = bookingRepository.findByUnitIdAndUserId(parseStringToLong(unitId), parseStringToLong(userId));
        if (booking.isEmpty()) {
            log.warn("No active booking found for unit {} and user {}", unitId, userId);
            throw new BookingNotFoundException(String.format("Booking is not found for unit %s and user %s", unitId, userId));
        }

        BookingEntity bookingEntity = booking.get();
        bookingRepository.delete(bookingEntity);
        log.info("Booking cancelled successfully for unit {} and user {}", unitId, userId);
        return true;
    }

    @CacheEvict(value = "availableUnits", allEntries = true)
    public void markUnitAsRented(String unitId) {
        Optional<UnitEntity> unitEntity = unitRepository.findById(parseStringToLong(unitId));
        unitEntity.ifPresent(entity -> entity.setAvailable(false));
        unitRepository.save(unitEntity.get());
    }

    @CacheEvict(value = "availableUnits", allEntries = true)
    public void markUnitAsAvailable(String unitId) {
        Optional<UnitEntity> unitEntity = unitRepository.findById(parseStringToLong(unitId));
        unitEntity.ifPresent(entity -> entity.setAvailable(true));
        unitRepository.save(unitEntity.get());
    }
}
