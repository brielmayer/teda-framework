package com.brielmayer.teda.database.mysql;

import com.brielmayer.teda.handler.impl.LogExecutionHandler;
import com.brielmayer.teda.Teda;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class MySqlSuiteTest {

    @Container
    public static MySQLContainer<?> mySqlContainer8_0_31 = new MySQLContainer<>("mysql:8.0.31");

    @Container
    public static MySQLContainer<?> mySqlContainer5_7_40 = new MySQLContainer<>("mysql:5.7.40");

    private BaseDatabase database;

    void initializeDatabase(MySQLContainer<?> container) {
        // Setup MySQL database
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(container.getJdbcUrl());
        dataSource.setUser(container.getUsername());
        dataSource.setPassword(container.getPassword());

        // Create and initialize database
        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.asString("database/mysql/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTestMySql5() {
        initializeDatabase(mySqlContainer5_7_40);
        new Teda(database.getDataSource(), new LogExecutionHandler())
                .execute(ResourceReader.asInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }

    @Test
    void loadTestMySql8() {
        initializeDatabase(mySqlContainer8_0_31);
        new Teda(database.getDataSource(), new LogExecutionHandler())
                .execute(ResourceReader.asInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
