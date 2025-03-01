package com.bookingsystem.utils;

import com.bookingsystem.exception.NotValidArgumentException;
import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.repository.entity.UnitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.function.Function;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConversionUtilsTest {

    @Test
    public void testParseStringToLong_valid() {
        long result = ConversionUtils.parseStringToLong("123");
        assertEquals(123L, result);
    }

    @Test
    public void testParseStringToLong_invalid() {
        assertThrows(NotValidArgumentException.class, () -> ConversionUtils.parseStringToLong("abc"));
    }

    @Test
    public void testResolveResponseStatus_withValidStatus() {
        String response = "test";
        Function<String, Integer> statusExtractor = s -> 404;
        HttpStatus status = ConversionUtils.resolveResponseStatus(response, statusExtractor);
        assertEquals(HttpStatus.NOT_FOUND, status);
    }

    @Test
    public void testResolveResponseStatus_withNullStatus() {
        String response = "test";
        Function<String, Integer> statusExtractor = s -> null;
        HttpStatus status = ConversionUtils.resolveResponseStatus(response, statusExtractor);
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    public void testResolveResponseStatus_withNullResponse() {
        HttpStatus status = ConversionUtils.resolveResponseStatus(null, s -> 500);
        assertEquals(HttpStatus.OK, status);
    }

    @Test
    public void testCreateResponse_messageResponse() {
        String message = "Test message";
        int statusCode = 201;
        MessageResponse response = ConversionUtils.createResponse(message, statusCode);
        assertEquals(message, response.getMessage());
        assertEquals(statusCode, response.getStatus());
    }

    @Test
    public void testCreateResponse_unitListResponse() {
        List<Unit> units = List.of(new Unit());
        PageRequest pageable = PageRequest.of(0, 1);
        List<UnitEntity> unitEntities = List.of(new UnitEntity());
        Page<UnitEntity> unitPage = new PageImpl<>(unitEntities, pageable, 1);
        UnitListResponse response = ConversionUtils.createResponse(units, unitPage, 200);
        assertEquals(units, response.getContent());
        assertEquals(1, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
        assertEquals(1, response.getSize());
        assertEquals(200, response.getStatus());
    }
}