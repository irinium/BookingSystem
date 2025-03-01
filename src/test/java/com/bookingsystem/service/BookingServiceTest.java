package com.bookingsystem.service;

import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.UnitEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventService eventService;

    @InjectMocks
    private BookingService bookingService;

    @Test
    public void testBookUnitSuccess() {
        String unitId = "1";
        String userId = "10";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(5);
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setAvailable(true);
        when(unitRepository.findById(1L)).thenReturn(Optional.of(unitEntity));
        when(bookingRepository.countOverlappingBookings(1L, startDate, endDate)).thenReturn(0);
        when(bookingRepository.save(any(BookingEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(unitRepository.save(unitEntity)).thenReturn(unitEntity);
        MessageResponse response = bookingService.bookUnit(unitId, userId, startDate, endDate);
        String expectedMessage = String.format("Successfully booked unit with id %s for dates from %s till %s", unitId, startDate, endDate);
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testBookUnitUnitNotFound() {
        String unitId = "2";
        String userId = "10";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        when(unitRepository.findById(2L)).thenReturn(Optional.empty());
        MessageResponse response = bookingService.bookUnit(unitId, userId, startDate, endDate);
        String expectedMessage = String.format("Unit with id %s does not exist, booking cannot proceed", 2L);
        assertEquals(expectedMessage, response.getMessage());
    }

    @Test
    public void testCancelBookingSuccess() {
        String unitId = "1";
        String userId = "10";
        BookingEntity bookingEntity = new BookingEntity();
        when(bookingRepository.findByUnitIdAndUserId(1L, 10L)).thenReturn(Optional.of(bookingEntity));
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setAvailable(false);
        when(unitRepository.findById(1L)).thenReturn(Optional.of(unitEntity));
        when(unitRepository.save(any(UnitEntity.class))).thenReturn(unitEntity);
        MessageResponse response = bookingService.cancelBooking(unitId, userId);
        String expectedMessage = String.format("Successfully cancelled booking for unit %s", unitId);
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(200, response.getStatus());
        verify(bookingRepository).delete(bookingEntity);
    }

    @Test
    public void testCancelBookingBookingNotFound() {
        String unitId = "1";
        String userId = "10";
        when(bookingRepository.findByUnitIdAndUserId(1L, 10L)).thenReturn(Optional.empty());
        MessageResponse response = bookingService.cancelBooking(unitId, userId);
        String expectedMessage = String.format("Booking is not found for unit %s and user %s", unitId, userId);
        assertEquals(expectedMessage, response.getMessage());
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testMarkUnitAsRented() {
        String unitId = "1";
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setAvailable(true);
        when(unitRepository.findById(1L)).thenReturn(Optional.of(unitEntity));
        when(unitRepository.save(unitEntity)).thenReturn(unitEntity);
        bookingService.markUnitAsRented(unitId);
        assertFalse(unitEntity.getAvailable());
        verify(unitRepository).save(unitEntity);
    }

    @Test
    public void testMarkUnitAsAvailable() {
        String unitId = "1";
        UnitEntity unitEntity = new UnitEntity();
        unitEntity.setAvailable(false);
        when(unitRepository.findById(1L)).thenReturn(Optional.of(unitEntity));
        when(unitRepository.save(unitEntity)).thenReturn(unitEntity);
        bookingService.markUnitAsAvailable(unitId);
        assertTrue(unitEntity.getAvailable());
        verify(unitRepository).save(unitEntity);
    }
}
