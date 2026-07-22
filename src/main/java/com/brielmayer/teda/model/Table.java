package com.brielmayer.teda.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;
        final Table other = (Table) o;
        return Objects.equals(name, other.name)
                && Objects.equals(headers, other.headers)
                && Objects.equals(data, other.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, headers, data);
    }
}
