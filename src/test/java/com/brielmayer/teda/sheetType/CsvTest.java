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
        // DB_CLOSE_DELAY=-1 keeps the in-memory database alive between the
        // individual connections DbUtils opens for each executeQuery call.
        dataSource.setURL("jdbc:h2:mem:test-csv;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        database = DatabaseFactory.createDatabase(dataSource);
        // Reset between tests so each @Test starts with an empty STUDENT table.
        database.dropTable("STUDENT");
        database.executeQuery(ResourceReader.asString("database/h2/CREATE_TEST_TABLE.sql"));
    }

    @Test
    void loadTestFromCsvDirectory() throws Exception {
        final TedaConfiguration configuration = TedaConfiguration.builder()
                .withDatabase(database.getDataSource())
                .build();

        new Teda(configuration).execute(csvDirectory("teda/csv/loadTest"), DocumentType.CSV);
    }

    /**
     * Exercises CSV-specific features that go beyond the basic load test:
     * <ul>
     *   <li>Quoted values containing a comma (row 1: {@code "O'Brien, Alice"}).</li>
     *   <li>Escaped double-quotes inside a quoted value (row 4: {@code "Diana ""The Star"""}).</li>
     *   <li>Unquoted values with an interior space (row 3: {@code Charlie Brown}).</li>
     *   <li>Five data rows to confirm the parser does not stop at some hidden limit.</li>
     *   <li>Expected rows given in reverse order to confirm Teda sorts both sides
     *       by the {@code #id} primary key before comparing.</li>
     *   <li>Nine-digit decimal averages to exercise the {@code decimal(18, 9)}
     *       column type without precision loss.</li>
     * </ul>
     */
    @Test
    void loadTestWithQuotedValuesAndReorderedExpected() throws Exception {
        final TedaConfiguration configuration = TedaConfiguration.builder()
                .withDatabase(database.getDataSource())
                .build();

        new Teda(configuration).execute(csvDirectory("teda/csv/complex"), DocumentType.CSV);
    }

    private Path csvDirectory(final String resourcePath) throws Exception {
        final URL resource = getClass().getClassLoader().getResource(resourcePath);
        if (resource == null) {
            throw new IllegalStateException("Test resource not found: " + resourcePath);
        }
        return Paths.get(resource.toURI());
    }
}
