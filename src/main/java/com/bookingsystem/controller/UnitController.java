package com.bookingsystem.controller;


import com.bookingsystem.controller.generated.UnitApi;
import com.bookingsystem.model.generated.*;
import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.service.BookingService;
import com.bookingsystem.service.UnitService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/unit")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UnitController implements UnitApi {

    private final UnitService unitService;
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
        return new ResponseEntity<>(unitService.createUnit(unit), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<GetUnitsResponse> getUnits(
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "minCost", required = false) BigDecimal minCost,
            @RequestParam(value = "maxCost", required = false) BigDecimal maxCost,
            @RequestParam(value = "accommodationType", required = false) String accommodationType,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        AccommodationType accomodation = AccommodationType.safelyValueOf(accommodationType);
        var units = unitService.findUnits(startDate, endDate, minCost, maxCost, accomodation, page, size);
        return ResponseEntity.ok(units);
    }

    @Override
    @PostMapping("/{unitId}/book")
    public ResponseEntity<BookingResponse> bookUnit(
            @PathVariable String unitId,
            @RequestBody BookingRequest request) {
        var response = bookingService.bookUnit(unitId, request.getUserId(),
                request.getBookingStartDate(), request.getBookingEndDate());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{unitId}/cancel")
    public ResponseEntity<CancelBookingResponse> cancelBooking(
            @PathVariable String unitId,
            @RequestBody CancelBookingRequest request) {
        boolean success = bookingService.cancelBooking(unitId, request.getUserId());
        CancelBookingResponse response = new CancelBookingResponse();
        response.setMessage(success ? "Cancellation successful" : "Cancellation failed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{unitId}/pay")
    public ResponseEntity<PaymentResponse> emulatePayment(
            @PathVariable String unitId,
            @RequestBody PaymentRequest request) {
        boolean success = unitService.emulatePayment(unitId, request.getUserId());
        PaymentResponse response = new PaymentResponse();
        response.setMessage(success ? "Payment successful" : "Payment failed");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

