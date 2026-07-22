package com.brielmayer.teda.parser;

/**
 * Normalizes any value to a canonical form suitable for cross-source comparison.
 * Combines {@link JdbcTypeNormalizer} (canonicalizes typed JDBC results and
 * pre-typed spreadsheet values) with {@link SpreadsheetStringDetector} (detects
 * the type of raw String values), applied in that order.
 * <p>
 * {@code null} is passed through unchanged. Comparators and assertion code
 * decide the semantics of null values.
 */
public final class TypeParser {

    private static final TypeNormalizer JDBC = new JdbcTypeNormalizer();
    private static final TypeNormalizer STRING = new SpreadsheetStringDetector();

    private TypeParser() {}

    public static Object parse(final Object value) {
        return STRING.normalize(JDBC.normalize(value));
    }
}
