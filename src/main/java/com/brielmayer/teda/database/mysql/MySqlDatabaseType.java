package com.brielmayer.teda.database.mysql;

import javax.sql.DataSource;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

public class MySqlDatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null && databaseProductName.toLowerCase().contains("mysql");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new MySqlDatabase(dataSource);
    }
}
