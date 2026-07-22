package com.brielmayer.teda.sheetType;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.brielmayer.teda.Teda;
import com.brielmayer.teda.configuration.TedaConfiguration;
import com.brielmayer.teda.database.BaseDatabase;
import com.brielmayer.teda.database.DatabaseFactory;
import com.brielmayer.teda.model.DocumentType;
import com.brielmayer.teda.util.ResourceReader;

public class CsvTest {

    private BaseDatabase database;

    @BeforeEach
    void setup() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test-csv;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        database = DatabaseFactory.createDatabase(dataSource);
        database.executeQuery(ResourceReader.asString("database/h2/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTestFromCsvDirectory() throws Exception {
        final TedaConfiguration configuration = TedaConfiguration.builder()
                .withDatabase(database.getDataSource())
                .build();

        final URL resource = getClass().getClassLoader().getResource("teda/loadTestCsv");
        final Path csvDirectory = Paths.get(resource.toURI());

        new Teda(configuration).execute(csvDirectory, DocumentType.CSV);
    }
}
