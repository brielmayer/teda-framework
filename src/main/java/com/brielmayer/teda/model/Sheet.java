package com.brielmayer.teda.model;

import java.util.Map;
import java.util.Objects;

public class Sheet {

    private final String name;
    private final Map<String, Table> tables;

    Sheet(final SheetBuilder builder) {
        this.name = builder.getName();
        this.tables = builder.getTables();
    }

    public String getName() {
        return name;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public static SheetBuilder builder() {
        return new SheetBuilder();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Sheet)) return false;
        final Sheet other = (Sheet) o;
        return Objects.equals(name, other.name) && Objects.equals(tables, other.tables);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tables);
    }
}
