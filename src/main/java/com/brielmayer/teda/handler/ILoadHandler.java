package com.brielmayer.teda.handler;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.model.Table;

public interface ILoadHandler {
    void load(final BaseDatabase database, final Table table);
}
