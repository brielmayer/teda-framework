package com.brielmayer.teda.model;

import java.util.List;
import java.util.Map;

public class Table {

    private final String name;
    private final List<Header> headers;
    private final List<Map<String, Object>> data;

    Table(final TableBuilder builder) {
        this.name = builder.getName();
        this.headers = builder.getHeaders();
        this.data = builder.getData();
    }

    public String getName() {
        return name;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public static TableBuilder builder() {
        return new TableBuilder();
    }
}
