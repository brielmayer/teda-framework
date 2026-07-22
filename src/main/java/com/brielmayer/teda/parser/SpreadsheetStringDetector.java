package com.brielmayer.teda.parser;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Pattern;

/**
 * Detects the intended type of a raw {@link String} value, typically from a
 * text-formatted spreadsheet cell. Non-String inputs are returned unchanged.
 * <p>
 * Recognizes:
 * <ul>
 *   <li>{@code true} / {@code false} (case-insensitive) as {@link Boolean}</li>
 *   <li>integer literals as {@link BigDecimal}</li>
 *   <li>decimal literals as {@link BigDecimal}</li>
 *   <li>ISO-8601 {@link LocalDate}, {@link LocalTime}, {@link LocalDateTime}</li>
 * </ul>
 * Anything else stays a String.
 */
final class SpreadsheetStringDetector implements TypeNormalizer {

    private static final Pattern BOOL_PATTERN   = Pattern.compile("^(true|false)$", Pattern.CASE_INSENSITIVE);
    private static final Pattern LONG_PATTERN   = Pattern.compile("^-?(0|[1-9][0-9]*)$");
    private static final Pattern DOUBLE_PATTERN = Pattern.compile("^-?(0|[1-9][0-9]*)\\.[0-9]+$");

    @Override
    public Object normalize(final Object value) {
        if (!(value instanceof String)) {
            return value;
        }
        final String s = (String) value;

        if (BOOL_PATTERN.matcher(s).matches()) {
            return Boolean.parseBoolean(s);
        }
        if (LONG_PATTERN.matcher(s).matches()) {
            return BigDecimal.valueOf(Long.parseLong(s));
        }
        if (DOUBLE_PATTERN.matcher(s).matches()) {
            return BigDecimal.valueOf(Double.parseDouble(s));
        }

        try {
            return LocalDate.parse(s);
        } catch (DateTimeException ignored) {
        }

        try {
            return LocalTime.parse(s);
        } catch (DateTimeException ignored) {
        }

        try {
            return LocalDateTime.parse(s);
        } catch (DateTimeException ignored) {
        }

        return s;
    }
}
