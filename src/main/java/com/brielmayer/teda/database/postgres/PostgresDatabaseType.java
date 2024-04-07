package com.brielmayer.teda.database.postgres;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;
import com.brielmayer.teda.database.postgres.PostgresDatabase;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PostgresDatabaseType implements DatabaseType {

    private static final Set<String> POSTGRES_CLASS_NAMES = new HashSet<>(Collections.singletonList(
            "org.postgresql.ds.PGSimpleDataSource"
    ));

    @Override
    public boolean handlesDataSource(DataSource dataSource) {
        return POSTGRES_CLASS_NAMES.contains(dataSource.getClass().getName());
    }

    @Override
    public BaseDatabase createDatabase(DataSource dataSource) {
        return new PostgresDatabase(dataSource);
    }
}
