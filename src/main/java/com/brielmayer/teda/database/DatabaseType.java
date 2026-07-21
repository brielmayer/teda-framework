package com.brielmayer.teda.database;

import javax.sql.DataSource;

public interface DatabaseType {

    /**
     * Decides whether this type handles the given database, based on the JDBC
     * {@link java.sql.DatabaseMetaData#getDatabaseProductName() product name}.
     * This works with any {@link DataSource} implementation, including connection
     * pools, unlike matching on the concrete DataSource class.
     */
    boolean handles(String databaseProductName);

    BaseDatabase createDatabase(DataSource dataSource);
}
