package com.brielmayer.teda.database.sqlserver;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

import javax.sql.DataSource;

public class SqlServerDatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null
                && databaseProductName.toLowerCase().contains("sql server");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new SqlServerDatabase(dataSource);
    }
}
