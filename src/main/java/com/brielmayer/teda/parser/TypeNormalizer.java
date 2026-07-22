package com.brielmayer.teda.parser;

/**
 * Converts a value from one Java representation to another canonical one.
 * Composable: chaining two normalizers applies the second to the result of the
 * first. Implementations must be side-effect free and thread-safe.
 */
@FunctionalInterface
public interface TypeNormalizer {

    Object normalize(Object value);
}
