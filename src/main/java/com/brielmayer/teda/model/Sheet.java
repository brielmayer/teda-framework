package com.brielmayer.teda.model;

import java.util.Map;

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
}
