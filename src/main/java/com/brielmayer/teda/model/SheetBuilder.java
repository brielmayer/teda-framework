package com.brielmayer.teda.model;

import java.util.Map;

public final class SheetBuilder {

    private String name;
    private Map<String, Table> tables;

    SheetBuilder() {}

    public SheetBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public SheetBuilder tables(final Map<String, Table> tables) {
        this.tables = tables;
        return this;
    }

    String getName() {
        return name;
    }

    Map<String, Table> getTables() {
        return tables;
    }

    public Sheet build() {
        return new Sheet(this);
    }
}
