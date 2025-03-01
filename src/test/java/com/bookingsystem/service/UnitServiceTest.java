package com.bookingsystem.service;

import com.bookingsystem.mapper.UnitMapper;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.UnitEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private UnitMapper unitMapper;

    @InjectMocks
    private UnitService unitService;

    @Test
    public void testCreateUnit_success() {
        com.bookingsystem.model.generated.Unit unit = new com.bookingsystem.model.generated.Unit();
        BigDecimal originalCost = BigDecimal.valueOf(100);
        unit.setCost(originalCost);
        UnitEntity unitEntity = new UnitEntity();
        when(unitMapper.toUnitEntity(unit)).thenReturn(unitEntity);
        when(unitRepository.save(unitEntity)).thenReturn(unitEntity);
        Unit convertedUnit = new Unit();
        convertedUnit.setCost(originalCost.add(originalCost.multiply(BigDecimal.valueOf(0.15))));
        when(unitMapper.toUnit(unitEntity)).thenReturn(convertedUnit);
        UnitListResponse response = unitService.createUnit(unit);
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        List<Unit> content = response.getContent();
        assertEquals(1, content.size());
        BigDecimal expectedCost = originalCost.add(originalCost.multiply(BigDecimal.valueOf(0.15)));
        assertEquals(expectedCost, content.get(0).getCost());
    }

    @Test
    public void testCreateUnit_nullUnit() {
        UnitListResponse response = unitService.createUnit(null);
        assertNotNull(response);
        assertEquals(400, response.getStatus());
        assertEquals(Collections.emptyList(), response.getContent());
    }

    @Test
    public void testFindUnits_success() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        BigDecimal minCost = BigDecimal.valueOf(50);
        BigDecimal maxCost = BigDecimal.valueOf(200);
        AccommodationType accommodationType = AccommodationType.HOME;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        UnitEntity unitEntity = new UnitEntity();
        List<UnitEntity> unitEntityList = List.of(unitEntity);
        Page<UnitEntity> unitEntityPage = new PageImpl<>(unitEntityList, pageable, unitEntityList.size());
        when(unitRepository.findAvailableUnits(minCost, maxCost, accommodationType, startDate, endDate, pageable))
                .thenReturn(unitEntityPage);
        Unit generatedUnit = new Unit();
        List<Unit> unitList = List.of(generatedUnit);
        Page<Unit> unitPageConverted = new PageImpl<>(unitList, pageable, unitList.size());
        when(unitMapper.toUnitPage(unitEntityPage)).thenReturn(unitPageConverted);
        UnitListResponse response = unitService.findUnits(startDate, endDate, minCost, maxCost, accommodationType, page, size);
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(unitList, response.getContent());
    }

    @Test
    public void testFindUnits_noUnitsFound() {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(3);
        BigDecimal minCost = BigDecimal.valueOf(50);
        BigDecimal maxCost = BigDecimal.valueOf(200);
        AccommodationType accommodationType = AccommodationType.HOME;
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        when(unitRepository.findAvailableUnits(minCost, maxCost, accommodationType, startDate, endDate, pageable))
                .thenReturn(null);
        UnitListResponse response = unitService.findUnits(startDate, endDate, minCost, maxCost, accommodationType, page, size);
        assertNotNull(response);
        assertEquals(404, response.getStatus());
        assertEquals(Collections.emptyList(), response.getContent());
    }
}
