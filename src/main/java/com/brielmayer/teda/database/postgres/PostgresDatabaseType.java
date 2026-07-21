package com.brielmayer.teda.database.postgres;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

import javax.sql.DataSource;

public class PostgresDatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null
                && databaseProductName.toLowerCase().contains("postgresql");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new PostgresDatabase(dataSource);
    }
}
