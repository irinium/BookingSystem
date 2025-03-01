package com.bookingsystem.bootstrap;

import com.bookingsystem.mapper.UnitMapper;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.UnitEntity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;

@Component
public class RandomUnitDataLoader implements ApplicationRunner {

    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    public RandomUnitDataLoader(UnitRepository unitRepository, UnitMapper unitMapper) {
        this.unitRepository = unitRepository;
        this.unitMapper = unitMapper;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Random random = new Random();
        for (int i = 1; i <= 90; i++) {
            Unit unit = new Unit();
            unit.setNumberOfRooms(random.nextInt(5) + 1);
            Unit.AccommodationTypeEnum[] types = Unit.AccommodationTypeEnum.values();
            unit.setAccommodationType(types[random.nextInt(types.length)]);
            unit.setFloor(random.nextInt(10) + 1);
            double costValue = 50 + (random.nextDouble() * 450);
            unit.setCost(BigDecimal.valueOf(costValue));
            unit.setDescription("Randomly generated unit #" + i);
            unit.setAvailable(random.nextBoolean());
            UnitEntity unitEntity = unitMapper.toUnitEntity(unit);
            unitRepository.save(unitEntity);
        }
    }
}
