package com.brielmayer.teda.database.h2;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brielmayer.teda.Teda;
import com.brielmayer.teda.configuration.TedaConfiguration;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;

public class H2Test {

    private BaseDatabase database;

    @BeforeEach
    void setup() {
        // Setup H2 database
        final JdbcDataSource dataSource = new JdbcDataSource();
        // DB_CLOSE_DELAY=-1 keeps the in-memory database alive for the lifetime of the
        // JVM. Without it the database is dropped as soon as no connection is open, which
        // now happens between statements because connections are closed properly.
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        // Create and initialize database
        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.asString("database/h2/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTest() {
        TedaConfiguration configuration = TedaConfiguration.builder()
                .withDatabase(database.getDataSource())
                .build();

        new Teda(configuration).execute(ResourceReader.asInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
