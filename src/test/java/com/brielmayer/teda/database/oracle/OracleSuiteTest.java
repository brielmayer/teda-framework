package com.brielmayer.teda.database.oracle;

import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.Teda;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import oracle.jdbc.datasource.impl.OracleDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;

@Testcontainers
public class OracleSuiteTest {

    @Container
    public static OracleContainer oracleContainer = new OracleContainer("gvenzl/oracle-xe:21-slim-faststart")
            .withDatabaseName("testDB")
            .withUsername("testUser")
            .withPassword("testPassword");

    private BaseDatabase database;

    @BeforeEach
    void setup() throws SQLException {
        // Setup Oracle database
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setURL(oracleContainer.getJdbcUrl());
        dataSource.setUser(oracleContainer.getUsername());
        dataSource.setPassword(oracleContainer.getPassword());

        // Create and initialize database
        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.getResourceAsString("database/oracle/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTest() {
        new Teda(database.getDataSource(), new LogExecutionHandler())
                .execute(ResourceReader.getResourceAsInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
