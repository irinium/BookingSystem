package com.bookingsystem.controller;

import com.bookingsystem.controller.generated.StatsApi;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.service.StatsService;
import com.bookingsystem.utils.ConversionUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class StatsController implements StatsApi {
    private final StatsService statsService;

    @GetMapping("/stats/available-units")
    public ResponseEntity<UnitListResponse> getAvailableUnitsCount(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size) {
        UnitListResponse response = statsService.getAvailableUnits(page, size);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
}

