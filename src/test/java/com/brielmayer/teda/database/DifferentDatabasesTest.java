package com.brielmayer.teda.database;

import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.Teda;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.postgresql.ds.PGSimpleDataSource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DifferentDatabasesTest {

    @Container
    public static MySQLContainer<?> mySqlContainer = new MySQLContainer<>("mysql:8.0.31");

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1");

    private BaseDatabase mysqlDatabase;
    private BaseDatabase postgresDatabase;

    @BeforeAll
    void setup() {
        // Setup MySQL database
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setPassword(mySqlContainer.getPassword());
        mysqlDataSource.setUrl(mySqlContainer.getJdbcUrl());
        mysqlDataSource.setUser(mySqlContainer.getUsername());
        mysqlDatabase = DatabaseFactory.createDatabase(mysqlDataSource);
        mysqlDatabase.executeQuery(ResourceReader.asString("database/mysql/CREATE_TEST_TABLE.sql"));

        // Setup PostgreSQL database
        PGSimpleDataSource postgresDataSource = new PGSimpleDataSource();
        postgresDataSource.setPassword(postgreSQLContainer.getPassword());
        postgresDataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        postgresDataSource.setUser(postgreSQLContainer.getUsername());
        postgresDatabase = DatabaseFactory.createDatabase(postgresDataSource);
        postgresDatabase.executeQuery(ResourceReader.asString("database/postgres/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void testWithTwoDifferentDatabaseTypes() {
        new Teda(mysqlDatabase.getDataSource(), postgresDatabase.getDataSource(), new LogExecutionHandler())
                .execute(ResourceReader.asInputStream("teda/DIFFERENT_DATABASE_TEST.xlsx"), DocumentType.EXCEL);
    }
}
