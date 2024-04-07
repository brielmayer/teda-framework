package com.brielmayer.teda.handler;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.exception.TedaException;
import com.brielmayer.teda.model.Table;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
public final class LoadHandler {

    public static void load(final BaseDatabase database, final Table table) {
        log.info("Load table: {}", table.getName());

        int rowCount = 1;
        for (final Map<String, Object> row : table.getData()) {
            try {
                database.insertRow(table.getName(), row);
            } catch (final SQLException e) {
                throw TedaException.builder()
                        .appendMessage("Failed to insert data into %s", table.getName())
                        .appendMessage("Row %d contains an error", rowCount)
                        .cause(e)
                        .build();
            }
            rowCount++;
        }
    }
}
