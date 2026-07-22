package com.brielmayer.teda.model;

import java.util.Map;

public class Document {

    private final Map<String, Sheet> sheets;

    public Document(final Map<String, Sheet> sheets) {
        this.sheets = sheets;
    }

    public Map<String, Sheet> getSheets() {
        return sheets;
    }
}
