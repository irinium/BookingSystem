package com.bookingsystem.service;

import com.bookingsystem.mapper.UnitMapper;
import com.bookingsystem.model.generated.Unit;
import com.bookingsystem.model.generated.UnitListResponse;
import com.bookingsystem.repository.UnitRepository;
import com.bookingsystem.repository.entity.UnitEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.bookingsystem.utils.ConversionUtils.createResponse;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatsService {
    private final UnitRepository unitRepository;
    private final UnitMapper unitMapper;

    @Cacheable(value = "availableUnits")
    public UnitListResponse getAvailableUnits(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UnitEntity> unitPage = unitRepository.getAvailableUnits(pageable);

        if (unitPage == null) {
            unitPage = new PageImpl<>(Collections.emptyList());
            return new UnitListResponse()
                    .content(Collections.emptyList())
                    .totalElements((int) unitPage.getTotalElements())
                    .totalPages(unitPage.getTotalPages())
                    .size(unitPage.getSize())
                    .status(404);
        }

        List<Unit> units = unitMapper.toUnitPage(unitPage).getContent();
        return new UnitListResponse()
                .content(units)
                .totalElements((int) unitPage.getTotalElements())
                .totalPages(unitPage.getTotalPages())
                .size(unitPage.getSize())
                .status(200);
    }
}
