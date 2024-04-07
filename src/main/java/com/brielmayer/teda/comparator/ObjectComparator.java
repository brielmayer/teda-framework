package com.brielmayer.teda.comparator;

import com.brielmayer.teda.exception.TedaException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class ObjectComparator {

    public static boolean compare(final Object o1, final Object o2) {

        if (!o1.getClass().getSimpleName().equals(o2.getClass().getSimpleName())) {
            throw TedaException.builder()
                    .appendMessage("Types are not equal %s and %s with o1 value %s", o1.getClass().getSimpleName(), o2.getClass().getSimpleName(), o1.toString())
                    .build();
        }

        if (o1 instanceof String) {
            return o1.equals(o2);
        }

        if (o1 instanceof Boolean) {
            return ((boolean) o1) == ((boolean) o2);
        }

        // java.sql.Date
        if (o1 instanceof LocalDate) {
            return ((LocalDate) o1).isEqual((LocalDate) o2);
        }

        // java.sql.Timestamp
        if (o1 instanceof LocalDateTime) {
            return ((LocalDateTime) o1).isEqual((LocalDateTime) o2);
        }

        // java.sql.Time
        if (o1 instanceof LocalTime) {
            return o1.equals(o2);
        }

        // java.math.BigInteger
        if (o1 instanceof BigInteger) {
            return o1.equals(o2);
        }

        // java.math.BigDecimal
        if (o1 instanceof BigDecimal) {
            return ((BigDecimal) o1).compareTo((BigDecimal) o2) == 0;
        }

        throw TedaException.builder()
                .appendMessage("Type %s not allowed", o1.getClass().getSimpleName())
                .build();
    }
}
