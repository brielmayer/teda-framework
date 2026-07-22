package com.brielmayer.teda.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public final class ResourceReader {

    private ResourceReader() {}

    public static InputStream asInputStream(String fileName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

    public static String asString(String fileName) {
        try (InputStream is = asInputStream(fileName)) {
            if (is == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
