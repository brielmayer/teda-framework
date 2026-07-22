package com.brielmayer.teda.database.h2;

import javax.sql.DataSource;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

public class H2DatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null && databaseProductName.toLowerCase().contains("h2");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new H2Database(dataSource);
    }
}
