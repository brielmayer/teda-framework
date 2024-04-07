package com.brielmayer.teda.database.h2;

import com.brielmayer.teda.Teda;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class H2Test {

    private BaseDatabase database;

    @BeforeEach
    void setup() {
        // Setup H2 database
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        // Create and initialize database
        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.asString("database/h2/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTest() {
        new Teda(database.getDataSource())
                .execute(ResourceReader.asInputStream("teda/LOAD_TEST.xlsx"), DocumentType.EXCEL);
    }
}
