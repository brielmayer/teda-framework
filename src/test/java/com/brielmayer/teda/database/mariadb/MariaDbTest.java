package com.brielmayer.teda.database.mariadb;

import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.Teda;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

@Testcontainers
public class MariaDbTest {

    @Container
    public static MariaDBContainer<?> mariaDbContainer = new MariaDBContainer<>("mariadb:10.9.4");

    private BaseDatabase database;

    @BeforeEach
    void initializeDatabase() throws SQLException {
        // Setup MariaDB database
        MariaDbDataSource dataSource = new MariaDbDataSource(mariaDbContainer.getJdbcUrl());
        dataSource.setUser(mariaDbContainer.getUsername());
        dataSource.setPassword(mariaDbContainer.getPassword());

        // Create and initialize database
        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.getResourceAsString("database/mariadb/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTest() {
        new Teda(database.getDataSource(), new LogExecutionHandler())
                .execute(ResourceReader.getResourceAsInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
