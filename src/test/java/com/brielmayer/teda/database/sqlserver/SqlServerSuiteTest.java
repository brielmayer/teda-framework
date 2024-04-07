package com.brielmayer.teda.database.sqlserver;

import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.Teda;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class SqlServerSuiteTest {

    @Container
    public static MSSQLServerContainer<?> mssqlServerContainer = new MSSQLServerContainer<>("mcr.microsoft.com/mssql/server:2017-CU12")
            .acceptLicense();

    private BaseDatabase database;

    @BeforeEach
    void setup() {
        // Setup SQL Server database
        SQLServerDataSource dataSource = new SQLServerDataSource();
        dataSource.setURL(mssqlServerContainer.getJdbcUrl());
        dataSource.setUser(mssqlServerContainer.getUsername());
        dataSource.setPassword(mssqlServerContainer.getPassword());

        // Create and initialize database
        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.asString("database/sqlserver/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTest() {
        new Teda(database.getDataSource(), new LogExecutionHandler())
                .execute(ResourceReader.asInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
