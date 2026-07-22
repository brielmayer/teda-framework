package com.brielmayer.teda.configuration;

import javax.sql.DataSource;

/**
 * Second stage of the {@link TedaConfiguration} fluent builder, reached after
 * {@link IDatabase#withLoadDatabase(DataSource)}. The caller must specify the
 * test database.
 */
public interface ITestDatabase {

    IBuild withTestDatabase(DataSource val);
}
