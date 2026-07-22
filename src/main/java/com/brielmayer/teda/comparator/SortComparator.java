package com.brielmayer.teda.comparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Header;
import com.brielmayer.teda.parser.TypeParser;

/**
 * Sorts rows by their primary key columns, in the order the keys are given.
 * <p>
 * Values are normalized through {@link TypeParser} before being compared, so the
 * ordering matches the equality semantics used in the comparison step and stays
 * consistent even when the expected (spreadsheet) and actual (database) rows use
 * different Java types for the same column (e.g. {@code Long} vs. {@code BigDecimal}).
 */
public class SortComparator implements Comparator<Map<String, Object>>, Serializable {

    private static final long serialVersionUID = 6561977888664706224L;

    private final List<Header> primaryKeys;

    public SortComparator(final List<Header> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    @Override
    public int compare(final Map<String, Object> o1, final Map<String, Object> o2) {
        for (final Header primaryKey : primaryKeys) {
            final String column = primaryKey.getName();

            final Object value1 = TypeParser.parse(o1.get(column));
            final Object value2 = TypeParser.parse(o2.get(column));

            final int result = compareValues(column, value1, value2);
            if (result != 0) {
                return result;
            }

            // equal on this primary key -> continue with the next one
        }

        // all primary keys are equal
        return 0;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static int compareValues(final String column, final Object value1, final Object value2) {
        // a null primary key would break row alignment; reject early with a
        // clear message rather than throwing NullPointerException below
        if (value1 == null || value2 == null) {
            throw TedaException.builder()
                    .appendMessage("Unable to sort data")
                    .appendMessage("Primary key \"%s\" contains null", column)
                    .build();
        }

        // within a single list the same column always yields the same type;
        // a mismatch means the primary key holds inconsistent data
        if (!value1.getClass().equals(value2.getClass())) {
            throw TedaException.builder()
                    .appendMessage("Unable to sort data")
                    .appendMessage("Data types are not equal within primary key \"%s\"", column)
                    .appendMessage(
                            "Can not compare \"%s\" with \"%s\"",
                            value1.getClass().getSimpleName(), value2.getClass().getSimpleName())
                    .build();
        }

        if (!(value1 instanceof Comparable)) {
            throw TedaException.builder()
                    .appendMessage("Unable to sort data")
                    .appendMessage(
                            "Primary key \"%s\" of type %s is not comparable",
                            column, value1.getClass().getSimpleName())
                    .build();
        }

        return ((Comparable) value1).compareTo(value2);
    }
}
