package com.brielmayer.teda.parser;

import com.brielmayer.teda.exception.TedaException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import java.util.regex.Pattern;

public class TypeParser {

    private static final Pattern BOOL_PATTERN = Pattern.compile("^(true|false)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern LONG_PATTERN = Pattern.compile("^-?(0|[1-9][0-9]*)$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^-?(0|[1-9][0-9]*).[0-9]+$");

    // ----------------------------------------------------
    // java.sql.Date        -> java.time.LocalDate
    // java.sql.Timestamp   -> java.time.LocalDateTime
    // java.sql.Time        -> java.time.LocalTime
    // ----------------------------------------------------
    // java.lang.Boolean    -> java.lang.Boolean
    // ----------------------------------------------------
    // java.lang.Byte       -> java.math.BigInteger
    // java.lang.Short      -> java.math.BigInteger
    // java.lang.Integer    -> java.math.BigInteger
    // java.lang.Long       -> java.math.BigInteger
    // java.math.BigInteger -> java.math.BigInteger
    // ----------------------------------------------------
    // java.lang.Float      -> java.math.BigDecimal
    // java.lang.Double     -> java.math.BigDecimal
    // java.math.BigDecimal -> java.math.BigDecimal
    // ----------------------------------------------------
    // java.util.UUID       -> java.lang.String
    // ----------------------------------------------------
    public static Object parse(Object value) {

        // default value is an empty String
        if (value == null) {
            return "";
        }

        if (value instanceof Boolean) {
            return value;
        }

        // java.sql.Date
        if (value instanceof Date) {
            Date date = (Date) value;
            return date.toLocalDate();
        }

        // java.sql.Timestamp
        if (value instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) value;
            return timestamp.toLocalDateTime();
        }

        // java.sql.Time
        if (value instanceof Time) {
            Time time = (Time) value;
            return time.toLocalTime();
        }

        // java.math.BigInteger
        if (value instanceof BigInteger) {
            return value;
        }

        // java.lang.Byte
        if (value instanceof Byte) {
            return BigInteger.valueOf((Byte) value);
        }

        // java.lang.Short
        if (value instanceof Short) {
            return BigInteger.valueOf((Short) value);
        }

        // java.lang.Integer
        if (value instanceof Integer) {
            return BigInteger.valueOf((Integer) value);
        }

        // java.lang.Long
        if (value instanceof Long) {
            return BigInteger.valueOf((Long) value);
        }

        // java.lang.Float
        if (value instanceof Float) {
            return new BigDecimal(Float.toString((Float)value));
        }

        // java.lang.Double
        if (value instanceof Double) {
            return new BigDecimal(Double.toString((Double)value));
        }

        // java.util.UUID
        if (value instanceof UUID) {
            UUID uuid = (UUID) value;
            return uuid.toString();
        }

        // java.math.BigDecimal
        if (value instanceof BigDecimal) {
            return value;
        }

        // from this point only try to parse strings
        if (!(value instanceof String)) {
            throw TedaException.builder()
                    .appendMessage("Type %s not supported", value.getClass().getSimpleName())
                    .build();
        }

        // java.lang.Boolean
        if (BOOL_PATTERN.matcher((String) value).matches()) {
            return Boolean.parseBoolean((String) value);
        }

        // java.lang.Long
        if (LONG_PATTERN.matcher((String) value).matches()) {
            return BigInteger.valueOf(Long.parseLong((String) value));
        }

        // java.lang.double
        if (DOUBLE_PATTERN.matcher((String) value).matches()) {
            return BigDecimal.valueOf(Double.parseDouble((String) value));
        }

        // java.time.LocalDateTime
        // ISO Date without offset: '2011-12-03'
        try {
            return LocalDate.parse((String) value);
        } catch (DateTimeException e) {
        }

        // java.time.LocalTime
        // Time without offset: '10:15:30'
        try {
            return LocalTime.parse((String) value);
        } catch (DateTimeException e) {
        }

        // java.time.LocalDateTime
        //  ISO Local Date and Time: '2011-12-03T10:15:30'
        try {
            return LocalDateTime.parse((String) value);
        } catch (DateTimeException e) {
        }

        return value;

    }

}
