package com.bookingsystem.service;

import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private EventService eventService;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void testEmulatePayment_NoBookingFound() {
        String unitId = "1";
        String userId = "10";
        when(bookingRepository.findByUnitIdAndUserId(1L, 10L)).thenReturn(Optional.empty());
        MessageResponse response = paymentService.emulatePayment(unitId, userId);
        assertEquals("No booking found for given unit and user", response.getMessage());
        assertEquals(404, response.getStatus());
    }

    @Test
    public void testEmulatePayment_AlreadyPaid() {
        String unitId = "1";
        String userId = "10";
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(100L);
        bookingEntity.setPaymentStatus(PaymentStatus.PAID);
        when(bookingRepository.findByUnitIdAndUserId(1L, 10L)).thenReturn(Optional.of(bookingEntity));
        MessageResponse response = paymentService.emulatePayment(unitId, userId);
        assertEquals("Booking is already paid", response.getMessage());
        assertEquals(400, response.getStatus());
    }

    @Test
    public void testEmulatePayment_Success() {
        String unitId = "1";
        String userId = "10";
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setId(200L);
        bookingEntity.setPaymentStatus(null);
        when(bookingRepository.findByUnitIdAndUserId(1L, 10L)).thenReturn(Optional.of(bookingEntity));
        when(bookingRepository.save(bookingEntity)).thenReturn(bookingEntity);
        MessageResponse response = paymentService.emulatePayment(unitId, userId);
        assertEquals("Payment successful", response.getMessage());
        assertEquals(200, response.getStatus());
        assertEquals(PaymentStatus.PAID, bookingEntity.getPaymentStatus());
    }
}