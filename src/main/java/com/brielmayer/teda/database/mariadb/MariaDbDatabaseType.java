package com.brielmayer.teda.database.mariadb;

import javax.sql.DataSource;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

public class MariaDbDatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null && databaseProductName.toLowerCase().contains("mariadb");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new MariaDbDatabase(dataSource);
    }
}
