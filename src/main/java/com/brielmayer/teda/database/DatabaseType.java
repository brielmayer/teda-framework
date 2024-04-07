package com.brielmayer.teda.database;

import javax.sql.DataSource;

public interface DatabaseType {

    boolean handlesDataSource(DataSource dataSource);
    BaseDatabase createDatabase(DataSource dataSource);
}
