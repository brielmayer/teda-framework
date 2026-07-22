package com.brielmayer.teda.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

/**
 * Normalizes typed Java values (as returned by JDBC drivers or spreadsheet
 * parsers) to a canonical form so equality checks are independent of the source
 * database or file format.
 * <p>
 * Integer types ({@code Byte}, {@code Short}, {@code Integer}, {@code Long},
 * {@code BigInteger}) collapse to {@link BigDecimal}. Floating-point types
 * ({@code Float}, {@code Double}) collapse to {@link BigDecimal} via
 * {@code toString} to strip precision artifacts. SQL date/time types become
 * their {@code java.time} counterparts. {@link UUID} becomes its {@link String}
 * form. {@code null} and unknown types are returned unchanged.
 */
final class JdbcTypeNormalizer implements TypeNormalizer {

    private static final Map<Class<?>, Function<Object, Object>> CONVERTERS = Map.ofEntries(
            Map.entry(Byte.class, v -> BigDecimal.valueOf((Byte) v)),
            Map.entry(Short.class, v -> BigDecimal.valueOf((Short) v)),
            Map.entry(Integer.class, v -> BigDecimal.valueOf((Integer) v)),
            Map.entry(Long.class, v -> BigDecimal.valueOf((Long) v)),
            Map.entry(BigInteger.class, v -> new BigDecimal((BigInteger) v)),
            Map.entry(Float.class, v -> new BigDecimal(Float.toString((Float) v))),
            Map.entry(Double.class, v -> new BigDecimal(Double.toString((Double) v))),
            Map.entry(Date.class, v -> ((Date) v).toLocalDate()),
            Map.entry(Time.class, v -> ((Time) v).toLocalTime()),
            Map.entry(Timestamp.class, v -> ((Timestamp) v).toLocalDateTime()),
            Map.entry(UUID.class, Object::toString));

    @Override
    public Object normalize(final Object value) {
        if (value == null) {
            return null;
        }
        final Function<Object, Object> converter = CONVERTERS.get(value.getClass());
        return converter == null ? value : converter.apply(value);
    }
}
