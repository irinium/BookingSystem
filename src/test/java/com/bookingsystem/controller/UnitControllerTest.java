package com.bookingsystem.controller;

import com.bookingsystem.model.generated.BookingRequest;
import com.bookingsystem.model.generated.CancelBookingRequest;
import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.model.generated.PaymentRequest;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.service.BookingService;
import com.bookingsystem.service.PaymentService;
import com.bookingsystem.service.UnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UnitController.class)
public class UnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UnitService unitService;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private PaymentService paymentService;

    @Test
    public void testCreateUnit() throws Exception {
        Unit unit = new Unit();
        unit.setId(1);
        unit.setNumberOfRooms(3);
        unit.setAccommodationType(Unit.AccommodationTypeEnum.FLAT);
        unit.setFloor(2);
        unit.setCost(BigDecimal.valueOf(120.00));
        unit.setDescription("Test unit");
        unit.setAvailable(true);
        UnitListResponse response = new UnitListResponse();
        response.setContent(Collections.singletonList(unit));
        response.setTotalElements(1);
        response.setTotalPages(1);
        response.setSize(1);
        response.setStatus(200);
        Mockito.when(unitService.createUnit(any(Unit.class))).thenReturn(response);
        mockMvc.perform(post("/api/unit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(unit)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    public void testGetUnits() throws Exception {
        Unit unit = new Unit();
        unit.setId(1);
        unit.setNumberOfRooms(3);
        unit.setAccommodationType(Unit.AccommodationTypeEnum.FLAT);
        unit.setFloor(2);
        unit.setCost(BigDecimal.valueOf(120.00));
        unit.setDescription("Test unit");
        unit.setAvailable(true);
        UnitListResponse response = new UnitListResponse();
        response.setContent(Collections.singletonList(unit));
        response.setTotalElements(1);
        response.setTotalPages(1);
        response.setSize(1);
        response.setStatus(200);
        Mockito.when(unitService.findUnits(any(), any(), any(), any(), any(), anyInt(), anyInt())).thenReturn(response);
        mockMvc.perform(get("/api/unit")
                        .param("startDate", "2025-03-01")
                        .param("endDate", "2025-03-15")
                        .param("minCost", "100")
                        .param("maxCost", "200")
                        .param("accommodationType", "FLAT")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200));
    }

    @Test
    public void testBookUnit() throws Exception {
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setUserId("1001");
        bookingRequest.setBookingStartDate(LocalDate.of(2025, 3, 1));
        bookingRequest.setBookingEndDate(LocalDate.of(2025, 3, 15));
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Booking successful");
        messageResponse.setStatus(200);
        Mockito.when(bookingService.bookUnit(anyString(), anyString(), any(), any())).thenReturn(messageResponse);
        mockMvc.perform(post("/api/unit/1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Booking successful"));
    }

    @Test
    public void testCancelBooking() throws Exception {
        CancelBookingRequest cancelRequest = new CancelBookingRequest();
        cancelRequest.setUserId("1001");
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Cancellation successful");
        messageResponse.setStatus(200);
        Mockito.when(bookingService.cancelBooking(anyString(), anyString())).thenReturn(messageResponse);
        mockMvc.perform(post("/api/unit/1/cancel")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cancelRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cancellation successful"));
    }

    @Test
    public void testEmulatePayment() throws Exception {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUserId("1001");
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage("Payment successful");
        messageResponse.setStatus(200);
        Mockito.when(paymentService.emulatePayment(anyString(), anyString())).thenReturn(messageResponse);
        mockMvc.perform(post("/api/unit/1/pay")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paymentRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Payment successful"));
    }
}
