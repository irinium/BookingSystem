package com.bookingsystem.controller;


import com.bookingsystem.controller.generated.UnitApi;
import com.bookingsystem.model.generated.*;
import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.service.BookingService;
import com.bookingsystem.service.PaymentService;
import com.bookingsystem.service.UnitService;
import io.micrometer.common.util.StringUtils;
import jodd.util.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.bookingsystem.utils.ConversionUtils.resolveResponseStatus;

@RestController
@RequestMapping("/api/unit")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UnitController implements UnitApi {

    private final UnitService unitService;
    private final BookingService bookingService;
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<UnitListResponse> createUnit(@RequestBody Unit unit) {
        UnitListResponse response = unitService.createUnit(unit);
        return new ResponseEntity<>(response,resolveResponseStatus(response, UnitListResponse::getStatus));
    }

    @GetMapping
    public ResponseEntity<UnitListResponse> getUnits(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "minCost", required = false) BigDecimal minCost,
            @RequestParam(value = "maxCost", required = false) BigDecimal maxCost,
            @RequestParam(value = "accommodationType", required = false) String accommodationType,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        AccommodationType accommodation = !StringUtils.isBlank(accommodationType) ? AccommodationType.safelyValueOf(accommodationType) : null;
        var response = unitService.findUnits(startDate, endDate, minCost, maxCost, accommodation, page, size);
        return new ResponseEntity<>(response, resolveResponseStatus(response, UnitListResponse::getStatus));
    }

    @Override
    @PostMapping("/{unitId}/book")
    public ResponseEntity<MessageResponse> bookUnit(
            @PathVariable String unitId,
            @RequestBody BookingRequest request) {
        var response = bookingService.bookUnit(unitId, request.getUserId(),
                request.getBookingStartDate(), request.getBookingEndDate());
        return new ResponseEntity<>(response, resolveResponseStatus(response, MessageResponse::getStatus));
    }

    @PostMapping("/{unitId}/cancel")
    public ResponseEntity<MessageResponse> cancelBooking(
            @PathVariable String unitId,
            @RequestBody CancelBookingRequest request) {
        MessageResponse response = bookingService.cancelBooking(unitId, request.getUserId());
        return new ResponseEntity<>(response, resolveResponseStatus(response, MessageResponse::getStatus));
    }

    @PostMapping("/{unitId}/pay")
    public ResponseEntity<MessageResponse> emulatePayment(
            @PathVariable String unitId,
            @RequestBody PaymentRequest request) {
        MessageResponse response = paymentService.emulatePayment(unitId, request.getUserId());
        return new ResponseEntity<>(response, resolveResponseStatus(response,MessageResponse::getStatus));
    }
}

