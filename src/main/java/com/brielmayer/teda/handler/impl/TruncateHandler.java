package com.brielmayer.teda.handler.impl;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.handler.ITruncateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TruncateHandler implements ITruncateHandler {

    private static final Logger log = LoggerFactory.getLogger(TruncateHandler.class);

    public void truncate(final BaseDatabase database, final String tableName) {
        log.info("Truncate table: {}", tableName);
        database.truncateTable(tableName);
    }
}
