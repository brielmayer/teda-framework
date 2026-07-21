package com.brielmayer.teda.handler.impl;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.handler.ITruncateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TruncateHandler implements ITruncateHandler {

    public void truncate(final BaseDatabase database, final String tableName) {
        log.info("Truncate table: {}", tableName);
        database.truncateTable(tableName);
    }
}
