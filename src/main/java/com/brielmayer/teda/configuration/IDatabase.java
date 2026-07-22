package com.brielmayer.teda.configuration;

import javax.sql.DataSource;

/**
 * First stage of the {@link TedaConfiguration} fluent builder. The caller must
 * choose between a single database (used for both load and test) or two
 * distinct databases.
 */
public interface IDatabase {

    IBuild withDatabase(DataSource val);

    ITestDatabase withLoadDatabase(DataSource val);
}
