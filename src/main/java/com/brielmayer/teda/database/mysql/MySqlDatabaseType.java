package com.brielmayer.teda.database.mysql;

import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseType;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MySqlDatabaseType implements DatabaseType {

    private static final Set<String> MYSQL_CLASS_NAMES = new HashSet<>(Collections.singletonList(
            "com.mysql.cj.jdbc.MysqlDataSource"
    ));

    @Override
    public boolean handlesDataSource(DataSource dataSource) {
        return MYSQL_CLASS_NAMES.contains(dataSource.getClass().getName());
    }

    @Override
    public BaseDatabase createDatabase(DataSource dataSource) {
        return new MySqlDatabase(dataSource);
    }
}
