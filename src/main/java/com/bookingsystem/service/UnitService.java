package com.bookingsystem.service;

import com.bookingsystem.mapper.UnitMapper;
import com.bookingsystem.model.generated.GetUnitsResponse;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.UnitEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UnitService {

    private UnitRepository unitRepository;
    private UnitMapper unitMapper;

    public Unit createUnit(Unit unit) {
        BigDecimal originalCost = unit.getCost();
        BigDecimal markup = originalCost.multiply(BigDecimal.valueOf(0.15));
        unit.setCost(originalCost.add(markup));
        UnitEntity unitEntity = unitRepository.save(unitMapper.toUnitEntity(unit));
        return unitMapper.toUnit(unitEntity);
    }

    public GetUnitsResponse findUnits(LocalDate startDate, LocalDate endDate, BigDecimal minCost, BigDecimal maxCost, AccommodationType accommodationType, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UnitEntity> unitPage = unitRepository.findAvailableUnits(minCost, maxCost, accommodationType, startDate, endDate, pageable);
        GetUnitsResponse response = new GetUnitsResponse();
        List<Unit> units = unitMapper.toUnitPage(unitPage).getContent();
        response.setContent(units);
        response.setTotalElements((int) unitPage.getTotalElements());
        response.setTotalPages(unitPage.getTotalPages());
        response.setSize(unitPage.getSize());
        return response;
    }

    public boolean emulatePayment(Long unitId, Long userId) {
        // TODO: Implement payment logic: update booking status upon successful payment, etc.
        return true;
    }


    public int getAvailableUnitsCount() {
        // TODO: Implement logic to count available Units (optionally using a cache)
        return 42;
    }
}
