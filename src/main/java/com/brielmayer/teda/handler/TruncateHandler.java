package com.brielmayer.teda.handler;

import com.brielmayer.teda.database.BaseDatabase;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TruncateHandler {

    public static void truncate(final BaseDatabase database, final String tableName) {
        log.info("Truncate table: {}", tableName);
        database.truncateTable(tableName);
    }
}
