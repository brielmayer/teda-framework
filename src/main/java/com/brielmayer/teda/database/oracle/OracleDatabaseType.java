package com.brielmayer.teda.database.oracle;

import javax.sql.DataSource;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

public class OracleDatabaseType implements DatabaseType {

    @Override
    public boolean handles(final String databaseProductName) {
        return databaseProductName != null && databaseProductName.toLowerCase().contains("oracle");
    }

    @Override
    public BaseDatabase createDatabase(final DataSource dataSource) {
        return new OracleDatabase(dataSource);
    }
}
