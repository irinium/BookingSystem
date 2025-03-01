package com.bookingsystem.mapper;

import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.repository.entity.AccommodationType;
import com.bookingsystem.repository.entity.UnitEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class UnitMapperTest {

    private final UnitMapper mapper = Mappers.getMapper(UnitMapper.class);

    @Test
    public void testToUnitEntity() {
        Unit unit = new Unit();
        unit.setId(1);
        unit.setNumberOfRooms(3);
        unit.setAccommodationType(Unit.AccommodationTypeEnum.FLAT);
        unit.setFloor(2);
        unit.setCost(BigDecimal.valueOf(120.00));
        unit.setDescription("Test description");
        unit.setAvailable(true);

        UnitEntity entity = mapper.toUnitEntity(unit);
        assertNotNull(entity);
        assertEquals(unit.getNumberOfRooms(), entity.getNumberOfRooms());
        assertEquals(unit.getAccommodationType().name(), entity.getAccommodationType().name());
        assertEquals(unit.getFloor(), entity.getFloor());
        assertEquals(unit.getCost(), entity.getCost());
        assertEquals(unit.getDescription(), entity.getDescription());
        assertEquals(unit.getAvailable(), entity.getAvailable());
    }

    @Test
    public void testToUnit() {
        UnitEntity entity = new UnitEntity();
        entity.setId(1L);
        entity.setNumberOfRooms(4);
        entity.setAccommodationType(AccommodationType.HOME);
        entity.setFloor(1);
        entity.setCost(BigDecimal.valueOf(200.00));
        entity.setDescription("Test unit entity");
        entity.setAvailable(false);

        Unit unit = mapper.toUnit(entity);
        assertNotNull(unit);
        assertEquals(entity.getNumberOfRooms(), unit.getNumberOfRooms());
        assertEquals(entity.getAccommodationType().name(), unit.getAccommodationType().name());
        assertEquals(entity.getFloor(), unit.getFloor());
        assertEquals(entity.getCost(), unit.getCost());
        assertEquals(entity.getDescription(), unit.getDescription());
        assertEquals(entity.getAvailable(), unit.getAvailable());
    }

    @Test
    public void testToUnitPage() {
        UnitEntity entity = new UnitEntity();
        entity.setId(1L);
        entity.setNumberOfRooms(2);
        entity.setAccommodationType(AccommodationType.APARTMENTS);
        entity.setFloor(3);
        entity.setCost(BigDecimal.valueOf(150.00));
        entity.setDescription("Apartment unit");
        entity.setAvailable(true);
        Page<UnitEntity> page = new PageImpl<>(Collections.singletonList(entity));

        Page<Unit> unitPage = mapper.toUnitPage(page);
        assertNotNull(unitPage);
        assertEquals(1, unitPage.getTotalElements());
        Unit unit = unitPage.getContent().get(0);
        assertEquals(entity.getNumberOfRooms(), unit.getNumberOfRooms());
        assertEquals(entity.getAccommodationType().name(), unit.getAccommodationType().name());
        assertEquals(entity.getFloor(), unit.getFloor());
        assertEquals(entity.getCost(), unit.getCost());
        assertEquals(entity.getDescription(), unit.getDescription());
        assertEquals(entity.getAvailable(), unit.getAvailable());
    }
}
