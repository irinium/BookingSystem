package com.bookingsystem.utils;

import com.bookingsystem.exception.NotValidArgumentException;
import com.bookingsystem.model.generated.MessageResponse;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.repository.entity.UnitEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.function.Function;

@Slf4j
public class ConversionUtils {
    public static long parseStringToLong(String id) {
        long parsedUnitId;
        try {
            parsedUnitId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.error("Error parsing unit id {}", id);
            throw new NotValidArgumentException(String.format("Error parsing unit id %s", id));
        }
        return parsedUnitId;
    }

    public static <T> HttpStatus resolveResponseStatus(T response, Function<T, Integer> statusExtractor) {
        if (response != null) {
            Integer statusValue = statusExtractor.apply(response);
            if (statusValue != null) {
                return HttpStatus.resolve(statusValue);
            }
        }
        return HttpStatus.OK;
    }

    public static MessageResponse createResponse(String e, int status) {
        return new MessageResponse(){{
            message(e);
            setStatus(status);
        }};
    }

    public static UnitListResponse createResponse(List<Unit> units, Page<UnitEntity> unitPage, int status) {
        return new UnitListResponse(){{
            setContent(units);
            setTotalElements((int) unitPage.getTotalElements());
            setTotalPages(unitPage.getTotalPages());
            setSize(unitPage.getSize());
            setStatus(status);
        }};
    }
}
