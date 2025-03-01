package com.bookingsystem.service;

import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.repository.BookingRepository;
import com.bookingsystem.repository.entity.BookingEntity;
import com.bookingsystem.repository.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.bookingsystem.utils.ConversionUtils.parseStringToLong;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final EventService eventService;

    public MessageResponse emulatePayment(String unitId, String userId) {
        MessageResponse response = new MessageResponse();
        Optional<BookingEntity> booking = bookingRepository.findByUnitIdAndUserId(parseStringToLong(unitId), parseStringToLong(userId));
        if (booking.isEmpty()) {
            log.warn("No booking found for unit {} and user {}", unitId, userId);
            response.setMessage("No booking found for given unit and user");
            response.setStatus(404);
            return response;
        }

        BookingEntity bookingEntity = booking.get();
        if (PaymentStatus.PAID == bookingEntity.getPaymentStatus()) {
            log.warn("Booking with id {} is already paid.", bookingEntity.getId());
            response.setMessage("Booking is already paid");
            response.setStatus(400);
            return response;
        }

        bookingEntity.setPaymentStatus(PaymentStatus.PAID);
        bookingRepository.save(bookingEntity);
        String msg = String.format("Payment successful for booking id %s.", bookingEntity.getId());
        eventService.logEvent("PAYMENT_PROCESSED", msg);
        log.info("Payment emulated successfully for booking id {}.", bookingEntity.getId());
        response.setMessage("Payment successful");
        response.setStatus(200);
        return response;
    }
}
