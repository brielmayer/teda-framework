package com.brielmayer.teda.database.sqlite;

import javax.sql.DataSource;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

public class SqliteDatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null && databaseProductName.toLowerCase().contains("sqlite");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new SqliteDatabase(dataSource);
    }
}
