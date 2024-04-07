package com.brielmayer.teda.database.h2;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class H2DatabaseType implements DatabaseType {

    private static final Set<String> H2_CLASS_NAMES = new HashSet<>(Collections.singletonList(
            "org.h2.jdbcx.JdbcDataSource"
    ));

    @Override
    public boolean handlesDataSource(DataSource dataSource) {
        return H2_CLASS_NAMES.contains(dataSource.getClass().getName());
    }

    @Override
    public BaseDatabase createDatabase(DataSource dataSource) {
        return new H2Database(dataSource);
    }
}
