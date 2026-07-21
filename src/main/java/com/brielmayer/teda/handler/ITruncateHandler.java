package com.brielmayer.teda.handler;

import com.brielmayer.teda.database.BaseDatabase;

public interface ITruncateHandler {
    void truncate(final BaseDatabase database, final String tableName);
}
