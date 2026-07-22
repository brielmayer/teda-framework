package com.brielmayer.teda.database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.brielmayer.teda.Teda;
import com.brielmayer.teda.configuration.TedaConfiguration;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import com.mysql.cj.jdbc.MysqlDataSource;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MultipleTablesInOneSheetTest {

    @Container
    public static MySQLContainer<?> mySqlContainer = new MySQLContainer<>("mysql:8.0.31");

    private BaseDatabase mysqlDatabase;

    @BeforeAll
    void setup() {
        // Setup MySQL database
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setPassword(mySqlContainer.getPassword());
        mysqlDataSource.setUrl(mySqlContainer.getJdbcUrl());
        mysqlDataSource.setUser(mySqlContainer.getUsername());
        mysqlDatabase = DatabaseFactory.createDatabase(mysqlDataSource);
        mysqlDatabase.executeQuery(ResourceReader.asString("database/mysql/CREATE_TEST_TABLE.sql"));
        mysqlDatabase.executeQuery(ResourceReader.asString("database/mysql/CREATE_TEST_TABLE_2.sql"));
    }

    @Test
    void testWithTwoDifferentDatabaseTypes() {
        TedaConfiguration tedaConfiguration = TedaConfiguration.builder()
                .withDatabase(mysqlDatabase.getDataSource())
                .build();

        new Teda(tedaConfiguration)
                .execute(ResourceReader.asInputStream("teda/MULTIPLE_TABLES_IN_ONE_SHEET.xlsx"), DocumentType.EXCEL);
    }
}
