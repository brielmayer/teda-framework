package com.brielmayer.teda.database.sqlite;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import com.brielmayer.teda.Teda;
import com.brielmayer.teda.configuration.TedaConfiguration;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;

public class SqliteTest {

    private Connection keepAlive;
    private BaseDatabase database;

    @BeforeEach
    void setup() throws SQLException {
        // Xerial's SQLite driver destroys the shared-cache in-memory DB as
        // soon as its last connection closes. We hold one connection open for
        // the lifetime of the test so all connections opened later (by
        // DbUtils' QueryRunner during executeQuery / insertRow / select) see
        // the same STUDENT table.
        final SQLiteConfig config = new SQLiteConfig();
        config.setSharedCache(true);

        final SQLiteDataSource dataSource = new SQLiteDataSource(config);
        dataSource.setUrl("jdbc:sqlite:file:teda-sqlite-test?mode=memory&cache=shared");

        keepAlive = dataSource.getConnection();

        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.asString("database/sqlite/CREATE_TEST_TABLE.sql"));
    }

    @AfterEach
    void teardown() throws SQLException {
        if (keepAlive != null && !keepAlive.isClosed()) {
            keepAlive.close();
        }
    }

    @Test
    void loadTest() {
        final TedaConfiguration configuration = TedaConfiguration.builder()
                .withDatabase(database.getDataSource())
                .build();

        new Teda(configuration).execute(ResourceReader.asInputStream("teda/xlsx/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
