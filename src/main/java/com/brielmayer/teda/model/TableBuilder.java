package com.brielmayer.teda.model;

import java.util.List;
import java.util.Map;

public final class TableBuilder {

    private String name;
    private List<Header> headers;
    private List<Map<String, Object>> data;

    TableBuilder() {}

    public TableBuilder name(final String name) {
        this.name = name;
        return this;
    }

    public TableBuilder headers(final List<Header> headers) {
        this.headers = headers;
        return this;
    }

    public TableBuilder data(final List<Map<String, Object>> data) {
        this.data = data;
        return this;
    }

    String getName() {
        return name;
    }

    List<Header> getHeaders() {
        return headers;
    }

    List<Map<String, Object>> getData() {
        return data;
    }

    public Table build() {
        return new Table(this);
    }
}
