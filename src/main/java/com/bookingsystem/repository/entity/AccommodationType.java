package com.bookingsystem.repository.entity;

import com.bookingsystem.exception.NotValidArgumentException;
import jodd.util.StringUtil;

public enum AccommodationType {
    HOME,
    FLAT,
    APARTMENTS;

    public static AccommodationType safelyValueOf(String value) {
        if (StringUtil.isBlank(value)) {
            throw new NotValidArgumentException("AccommodationType value cannot be blank");
        }
        try {
            return AccommodationType.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new NotValidArgumentException(e.getMessage());
        }
    }
}
