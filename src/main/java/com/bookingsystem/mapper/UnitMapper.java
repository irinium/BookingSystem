package com.bookingsystem.mapper;

import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.repository.entity.UnitEntity;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class UnitMapper {

    @Mapping(target = "numberOfRooms", source = "numberOfRooms")
    @Mapping(target = "accommodationType", source = "accommodationType")
    @Mapping(target = "floor", source = "floor")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "available", source = "available")
    public abstract UnitEntity toUnitEntity(Unit unit);

    @Mapping(target = "numberOfRooms", source = "numberOfRooms")
    @Mapping(target = "accommodationType", source = "accommodationType")
    @Mapping(target = "floor", source = "floor")
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "available", source = "available")
    public abstract Unit toUnit(UnitEntity unit);

    public Page<Unit> toUnitPage (Page<UnitEntity> unitEntities){
        return unitEntities.map(this::toUnit);
    }
}
