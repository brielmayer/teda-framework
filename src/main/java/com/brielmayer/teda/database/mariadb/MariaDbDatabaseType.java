package com.brielmayer.teda.database.mariadb;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MariaDbDatabaseType implements DatabaseType {

    private static final Set<String> MARIADB_CLASS_NAMES = new HashSet<>(Collections.singletonList(
            "org.mariadb.jdbc.MariaDbDataSource"
    ));

    @Override
    public boolean handlesDataSource(DataSource dataSource) {
        return MARIADB_CLASS_NAMES.contains(dataSource.getClass().getName());
    }

    @Override
    public BaseDatabase createDatabase(DataSource dataSource) {
        return new MariaDbDatabase(dataSource);
    }
}
