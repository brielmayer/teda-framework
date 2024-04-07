package com.brielmayer.teda.database.oracle;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;
import com.brielmayer.teda.database.oracle.OracleDatabase;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class OracleDatabaseType implements DatabaseType {

    private static final Set<String> ORACLE_CLASS_NAMES = new HashSet<>(Collections.singletonList(
            "oracle.jdbc.datasource.impl.OracleDataSource"
    ));

    @Override
    public boolean handlesDataSource(DataSource dataSource) {
        return ORACLE_CLASS_NAMES.contains(dataSource.getClass().getName());
    }

    @Override
    public BaseDatabase createDatabase(DataSource dataSource) {
        return new OracleDatabase(dataSource);
    }
}
