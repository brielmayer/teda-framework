package com.brielmayer.teda.database.sqlserver;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SqlServerDatabaseType implements DatabaseType {

    private static final Set<String> SQL_SERVER_CLASS_NAMES = new HashSet<>(Collections.singletonList(
            "com.microsoft.sqlserver.jdbc.SQLServerDataSource"
    ));

    @Override
    public boolean handlesDataSource(DataSource dataSource) {
        return SQL_SERVER_CLASS_NAMES.contains(dataSource.getClass().getName());
    }

    @Override
    public BaseDatabase createDatabase(DataSource dataSource) {
        return new SqlServerDatabase(dataSource);
    }
}
