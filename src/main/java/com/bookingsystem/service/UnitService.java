package com.bookingsystem.service;

import com.bookingsystem.mapper.UnitMapper;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.UnitEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.bookingsystem.utils.ConversionUtils.createResponse;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UnitService {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;
    private final EventService eventService;

    @CacheEvict(value = "availableUnits", allEntries = true)
    public UnitListResponse createUnit(Unit unit) {
        if (unit != null) {
            BigDecimal originalCost = unit.getCost();
            BigDecimal markup = originalCost.multiply(BigDecimal.valueOf(0.15));
            unit.setCost(originalCost.add(markup));
            UnitEntity unitEntity = unitRepository.save(unitMapper.toUnitEntity(unit));
            eventService.logEvent("UNIT_CREATION", "Unit created with id " + unitEntity.getId());
            return new UnitListResponse()
                    .status(200)
                    .content(List.of(unitMapper.toUnit(unitEntity)))
                    .message("Unit successfully saved.");
        } else {
            return new UnitListResponse()
                    .status(400)
                    .content(Collections.emptyList())
                    .message("Invalid input: unit is null.");
        }
    }

    public UnitListResponse findUnits(LocalDate startDate, LocalDate endDate, BigDecimal minCost, BigDecimal maxCost, AccommodationType accommodationType, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UnitEntity> unitPage = unitRepository.findAvailableUnits(minCost, maxCost, accommodationType, startDate, endDate, pageable);
        if (unitPage != null) {
            List<Unit> units = unitMapper.toUnitPage(unitPage).getContent();
            return createResponse(units, unitPage, 200);
        } else {
            Page<UnitEntity> emptyUnitPage = new PageImpl<>(Collections.emptyList());
            return createResponse(Collections.emptyList(), emptyUnitPage, 404);
        }
    }
}
