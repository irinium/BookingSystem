package com.bookingsystem.service;

import com.bookingsystem.mapper.UnitMapper;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.repository.UnitRepository;
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
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private UnitMapper unitMapper;

    @InjectMocks
    private StatsService statsService;

    @Test
    public void testGetAvailableUnitsCount_whenUnitsFound() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        UnitEntity unitEntity = new UnitEntity();
        List<UnitEntity> unitEntityList = List.of(unitEntity);
        Page<UnitEntity> unitEntityPage = new PageImpl<>(unitEntityList, pageable, unitEntityList.size());
        when(unitRepository.getAvailableUnits(pageable)).thenReturn(unitEntityPage);
        Unit unit = new Unit();
        List<Unit> unitList = List.of(unit);
        Page<Unit> unitPageConverted = new PageImpl<>(unitList, pageable, unitList.size());
        when(unitMapper.toUnitPage(unitEntityPage)).thenReturn(unitPageConverted);
        UnitListResponse response = statsService.getAvailableUnits(page, size);
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals(unitList, response.getContent());
    }

    @Test
    public void testGetAvailableUnitsCount_whenNoUnitsFound() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        when(unitRepository.getAvailableUnits(pageable)).thenReturn(null);
        UnitListResponse response = statsService.getAvailableUnits(page, size);
        assertNotNull(response);
        assertEquals(404, response.getStatus());
        assertEquals(Collections.emptyList(), response.getContent());
    }
}
