package com.brielmayer.teda.model;

import java.util.Objects;

public class Header {

    private static final String PRIMARY_KEY_PREFIX = "#";

    private final String name;
    private final boolean isPrimaryKey;

    private Header(final String name, final boolean isPrimaryKey) {
        this.name = name;
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getName() {
        return name;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public static Header fromName(String name) {
        if (name.startsWith(PRIMARY_KEY_PREFIX)) {
            return new Header(name.substring(1), true);
        } else {
            return new Header(name, false);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Header)) return false;
        final Header other = (Header) o;
        return isPrimaryKey == other.isPrimaryKey && Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isPrimaryKey);
    }

    @Override
    public String toString() {
        return (isPrimaryKey ? "#" : "") + name;
    }
}
