package com.brielmayer.teda.model;

import java.util.Map;
import java.util.Objects;

public class Document {

    private final Map<String, Sheet> sheets;

    public Document(final Map<String, Sheet> sheets) {
        this.sheets = sheets;
    }

    public Map<String, Sheet> getSheets() {
        return sheets;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Document)) return false;
        return Objects.equals(sheets, ((Document) o).sheets);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sheets);
    }
}
