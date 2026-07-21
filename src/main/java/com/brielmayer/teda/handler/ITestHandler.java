package com.brielmayer.teda.handler;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.model.Table;

public interface ITestHandler {
    void test(final BaseDatabase database, final Table expectedTable);
}
